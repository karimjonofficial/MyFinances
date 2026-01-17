package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.product.product1

val stockItem1 = StockItem(
    id = id1,
    product = product1,
    amount = 100,
    dateTime = dateTime,
    office = office1
)