package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.stock.GetStockItemByProduct

suspend fun getBasketItems(minItems: List<MinBasketItem>, repository: GetStockItemByProduct): List<BasketItem> {
    val items = minItems.map { minItem ->
        val stockItem = repository.getByProduct(minItem.id)
        if(stockItem != null) {
            BasketItem(
                product = stockItem.product,
                availableAmount = stockItem.amount,
                amount = minItem.amount,
                increaseEnabled = minItem.amount < stockItem.amount,
                decreaseEnabled = true
            )
        } else throw Exception()
    }

    return items
}
