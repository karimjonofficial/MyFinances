package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BasketRepository {
    private val mutex = Mutex()
    private val items = mutableListOf<MinBasketItem>()

    private val _events = MutableSharedFlow<BasketEvent>()
    val events = _events.asSharedFlow()

    suspend fun get(): List<MinBasketItem> = mutex.withLock {
        return items
    }

    suspend fun add(id: Id, amount: Int) {
        mutex.withLock {
            val index = indexOf(id)
            if(index != null) {
                val item = items[index]
                val newAmount = item.amount + amount
                items[index] = item.copy(amount = newAmount)
                _events.emit(BasketEvent.AmountChanged(id, newAmount))
            } else {
                items.add(MinBasketItem(id, amount))
                _events.emit(BasketEvent.FullRefresh)
            }
        }
    }

    suspend fun remove(id: Id, amount: Int) {
        mutex.withLock {
            val index = indexOf(id)
            if (index != null) {
                val item = items[index]
                val currentAmount = item.amount

                if (currentAmount > amount) {
                    val newAmount = currentAmount - amount
                    items[index] = item.copy(amount = newAmount)
                    _events.emit(BasketEvent.AmountChanged(id, newAmount))
                } else {
                    items.removeAt(index)
                    _events.emit(BasketEvent.ItemRemoved(id))
                }
            }
        }
    }

    suspend fun clear() {
        mutex.withLock {
            items.clear()
            _events.emit(BasketEvent.Clear)
        }
    }

    private fun find(id: Id): MinBasketItem? {
        return items.find { it.id == id }
    }

    private fun indexOf(id: Id): Int? {
        val item = find(id)
        return if(item != null) items.indexOf(item) else null
    }
}
