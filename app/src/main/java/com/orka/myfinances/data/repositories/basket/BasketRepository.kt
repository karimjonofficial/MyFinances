package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.product.ProductRepository

class BasketRepository(private val productRepository: ProductRepository) {
    private val items = mutableListOf<BasketItem>()

    fun get(): List<BasketItem> {
        return items.toList()
    }

    suspend fun add(productId: Id, amount: Int) {
        val index = items.indexOfFirst { it.product.id == productId }
        if(index != -1) {
            val i = items[index]
            items[index] = i.copy(amount = i.amount + amount)
        } else {
            val product = productRepository.getById(productId)
            items.add(BasketItem(product!!, amount))
        }
    }
}