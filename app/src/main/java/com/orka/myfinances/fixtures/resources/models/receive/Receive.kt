package com.orka.myfinances.fixtures.resources.models.receive

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.fixtures.resources.price
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val receive1 = Receive(
    id = id1,
    user = user1,
    items = receiveItems,
    price = price,
    dateTime = dateTime,
    description = description
)

@OptIn(ExperimentalTime::class)
val receive2 = Receive(
    id = id2,
    user = user1,
    items = receiveItems,
    price = price,
    dateTime = dateTime,
    description = description
)

val receives = listOf(receive1, receive2)