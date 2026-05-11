package com.orka.myfinances.data.repositories.basket

import android.util.Log
import com.orka.myfinances.data.api.product.ProductApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BasketRepository(private val client: HttpClient) {
    private val mutex = Mutex()
    private val items = mutableMapOf<Id, Int>()

    private val _events = MutableSharedFlow<BasketEvent>()
    val events = _events.asSharedFlow()

    suspend fun get(): List<BasketItem> = mutex.withLock {
        coroutineScope {
            items.map { (id, amount) ->
                async {
                    try {
                        val response = client.get("products/${id.value}/")
                        if (response.status == HttpStatusCode.OK) {
                            val product = response.body<ProductApiModel>()
                            BasketItem(product, amount)
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        Log.d("BasketRepository", "${e.message}")
                        null
                    }
                }
            }.awaitAll().filterNotNull()
        }
    }

    fun getCounts(): Map<Id, Int> = items.toMap()

    suspend fun add(id: Id, amount: Int) {
        mutex.withLock {
            items[id] = (items[id] ?: 0) + amount
            emit()
        }
    }

    suspend fun remove(id: Id, amount: Int) {
        mutex.withLock {
            val currentAmount = items[id] ?: 0
            if (currentAmount > amount) {
                items[id] = currentAmount - amount
            } else {
                items.remove(id)
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
}
