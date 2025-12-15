package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.sale.SaleItem

fun BasketItem.toSaleItem(id: Id): SaleItem {
    return SaleItem(id, product, amount)
}

fun List<BasketItem>.getPrice(): Int {
    return sumOf { it.product.salePrice * it.amount }
}