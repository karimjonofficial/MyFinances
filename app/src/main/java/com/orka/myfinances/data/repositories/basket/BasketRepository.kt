package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.product.ProductRepository

class BasketRepository(private val productRepository: ProductRepository) {
    private val items = mutableListOf<BasketItem>()

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

    fun clear() { items.clear() }

    private fun getIndex(id: Id): Int? {
        val index = items.indexOfFirst { it.product.id == id }
        return if(index == -1) null else index
    }
}