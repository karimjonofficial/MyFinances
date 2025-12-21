package com.orka.myfinances.fixtures.resources.models.order

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.fixtures.resources.models.client2
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.fixtures.resources.price
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val order1 = Order(
    id = id1,
    user = user1,
    client = client1,
    items = orderItems,
    price = price,
    dateTime = dateTime,
    endDateTime = dateTime,
    completed = false,
    description = description
)
@OptIn(ExperimentalTime::class)
val order2 = Order(
    id = id2,
    user = user1,
    client = client2,
    items = orderItems,
    price = price,
    dateTime = dateTime,
    endDateTime = dateTime,
    completed = true,
    description = description
)
val orders = listOf(order1, order2)