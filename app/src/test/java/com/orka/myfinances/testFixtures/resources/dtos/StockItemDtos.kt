package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.testFixtures.resources.dateTime

val stockItemDto1 = StockItemDto(
    id = 1,
    product = productDto1,
    amount = 10,
    dateTime = dateTime,
    createdAt = dateTime,
    modifiedAt = dateTime
)

val stockItemDtos = listOf(stockItemDto1)
