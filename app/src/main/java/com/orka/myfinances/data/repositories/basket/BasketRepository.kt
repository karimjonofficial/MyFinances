package com.orka.myfinances.data.repositories.basket

import android.util.Log
import com.orka.myfinances.data.api.product.ProductApiModel
import com.orka.myfinances.data.models.basket.BasketItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BasketRepository(private val client: HttpClient) {
    private val mutex = Mutex()
    private val items = mutableListOf<BasketItem>()

    private val _events = MutableSharedFlow<BasketEvent>()
    val events = _events.asSharedFlow()

    fun get(): List<BasketItem> {
        return items.toList()
    }

    suspend fun add(id: Int, amount: Int) {
        mutex.withLock {
            val index = getIndex(id)
            if (index != null) {
                val i = items[index]
                items[index] = i.copy(amount = i.amount + amount)
            } else {
                try {
                    val response = client.get("products/${id}/")
                    if (response.status == HttpStatusCode.OK) {
                        val product = response.body<ProductApiModel>()
                        items.add(BasketItem(product, amount))
                    }
                } catch (_: Exception) {
                    Log.d("BasketRepository", "add: error")
                }
            }
            emit()
        }
    }

    suspend fun remove(id: Int, amount: Int) {
        mutex.withLock {
            val index = getIndex(id)
            if (index != null) {
                val i = items[index]
                if (i.amount > amount)
                    items[index] = i.copy(amount = i.amount - amount)
                else items.removeAt(index)
            }
            emit()
        }
    }

    suspend fun clear() {
        mutex.withLock {
            items.clear()
            emit()
        }
    }

    private suspend fun emit() {
        _events.emit(BasketEvent)
    }

    private fun getIndex(id: Int): Int? {
        val index = items.indexOfFirst { it.product.id == id }
        return if (index == -1) null else index
    }
}
