package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2

val stockItem1 = StockItem(
    id = id1,
    product = product1,
    amount = 100,
    dateTime = dateTime,
    office = office1
)
val stockItem2 = StockItem(
    id = id2,
    product = product2,
    amount = 100,
    dateTime = dateTime,
    office = office1
)

val stockItems = listOf(stockItem1, stockItem2)