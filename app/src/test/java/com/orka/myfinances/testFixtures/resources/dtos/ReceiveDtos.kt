package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.dtos.receive.ReceiveItemDto
import com.orka.myfinances.testFixtures.resources.dateTime

val receiveItemDto1 = ReceiveItemDto(
    id = 1,
    productName = "Product 1",
    amount = 5
)

val receiveDto1 = ReceiveDto(
    id = 1,
    user = userDto1,
    branch = "Branch 1",
    price = 5000,
    dateTime = dateTime,
    items = listOf(receiveItemDto1),
    description = "Test Receive 1"
)

val receiveDtos = listOf(receiveDto1)
