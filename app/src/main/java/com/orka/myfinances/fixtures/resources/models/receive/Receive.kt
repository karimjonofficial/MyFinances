package com.orka.myfinances.fixtures.resources.models.receive

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.fixtures.resources.price

val receive1 = Receive(
    id = id1,
    user = user1,
    items = receiveItems,
    price = price,
    dateTime = dateTime,
    office = office1,
    description = "Shipment arrived via Northside Logistics. All items inspected for surface damage. Drill units batch #A-42 were slightly delayed but verified functional. Warehouse 4 storage zone B assigned for these units."
)
val receive2 = Receive(
    id = id2,
    user = user1,
    items = receiveItems,
    price = price,
    dateTime = dateTime,
    office = office1,
    description = description
)

val receives = listOf(receive1, receive2)