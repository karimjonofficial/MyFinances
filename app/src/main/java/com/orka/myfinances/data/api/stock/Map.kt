package com.orka.myfinances.data.api.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.product.Product

fun StockItemApiModel.map(product: Product): StockItem {
    return StockItem(
        id = Id(value = id),
        product = product,
        amount = amount,
        dateTime = createdAt
    )
}
