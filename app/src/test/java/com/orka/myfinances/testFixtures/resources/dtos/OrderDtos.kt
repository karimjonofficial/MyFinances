package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.dtos.order.OrderItemDto
import com.orka.myfinances.data.dtos.order.OrderProductDto
import com.orka.myfinances.testFixtures.resources.dateTime

val orderProductDto1 = OrderProductDto(
    id = 1,
    name = "Product 1",
    price = 1000,
    salePrice = 1100
)

val orderItemDto1 = OrderItemDto(
    id = 1,
    amount = 10,
    product = orderProductDto1
)

val orderDto1 = OrderDto(
    id = 1,
    user = userDto1,
    client = clientDto1,
    branch = branchDto1,
    price = 11000,
    endDateTime = dateTime,
    completed = false,
    completedDateTime = null,
    description = "Test Order 1",
    items = listOf(orderItemDto1),
    createdAt = dateTime
)

val orderDtos = listOf(orderDto1)
