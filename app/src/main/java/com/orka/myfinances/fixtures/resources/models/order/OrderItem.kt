package com.orka.myfinances.fixtures.resources.models.order

import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.fixtures.resources.amount
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.product1
import com.orka.myfinances.fixtures.resources.models.product2

val orderItem1 = OrderItem(
    id = id1,
    product = product1,
    amount = amount
)
val orderItem2 = OrderItem(
    id = id2,
    product = product2,
    amount = amount
)
val orderItems = listOf(orderItem1, orderItem2)