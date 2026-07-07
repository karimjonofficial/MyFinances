package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.dtos.sale.SaleItemDto
import com.orka.myfinances.testFixtures.resources.dateTime

val saleItemDto1 = SaleItemDto(
    id = 1,
    productName = "Product 1",
    amount = 2
)

val saleDto1 = SaleDto(
    id = 1,
    client = clientDto1,
    user = userDto1,
    items = listOf(saleItemDto1),
    dateTime = dateTime,
    price = 2000,
    description = "Test Sale 1"
)

val saleDtos = listOf(saleDto1)
