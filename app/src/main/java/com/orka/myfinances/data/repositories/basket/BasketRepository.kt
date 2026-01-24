package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.lib.data.repositories.GetByIdRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class BasketRepository(private val productRepository: GetByIdRepository<Product>) {
    private val items = mutableListOf<BasketItem>()

    private val _events = MutableSharedFlow<BasketEvent>()
    val events = _events.asSharedFlow()

    fun get(): List<BasketItem> {
        return items.toList()
    }

    suspend fun add(id: Id, amount: Int) {
        val index = getIndex(id)
        if(index != null) {
            val i = items[index]
            items[index] = i.copy(amount = i.amount + amount)
        } else {
            val product = productRepository.getById(id)
            items.add(BasketItem(product!!, amount))
        }
    }

    fun remove(id: Id, amount: Int) {
        val index = getIndex(id)
        if(index != null) {
            val i = items[index]
            if(i.amount > amount)
                items[index] = i.copy(amount = i.amount - amount)
            else items.removeAt(index)
        }
    }

    suspend fun clear() {
        items.clear()
        _events.emit(BasketEvent.Clear)
    }

    private fun getIndex(id: Id): Int? {
        val index = items.indexOfFirst { it.product.id == id }
        return if(index == -1) null else index
    }
}