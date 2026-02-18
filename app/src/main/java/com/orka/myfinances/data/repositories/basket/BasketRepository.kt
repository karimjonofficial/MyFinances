package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.lib.data.repositories.GetById
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BasketRepository(private val getById: GetById<Product>) {
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
                val product = getById.getById(id)
                if (product != null) {
                    items.add(BasketItem(product, amount))
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
