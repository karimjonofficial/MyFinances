package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.product.Product
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

    suspend fun add(id: Id, amount: Int) {
        mutex.withLock {
            val index = getIndex(id)
            if (index != null) {
                val i = items[index]
                items[index] = i.copy(amount = i.amount + amount)
            } else {
                try {
                    val response = client.get("products/${id.value}/")
                    if (response.status == HttpStatusCode.OK) {
                        val product = response.body<Product>()
                        items.add(BasketItem(product, amount))
                    }
                } catch (_: Exception) {
                    // Handle error
                }
            }
            emit()
        }
    }

    suspend fun remove(id: Id, amount: Int) {
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

    private fun getIndex(id: Id): Int? {
        val index = items.indexOfFirst { it.product.id == id }
        return if (index == -1) null else index
    }
}
