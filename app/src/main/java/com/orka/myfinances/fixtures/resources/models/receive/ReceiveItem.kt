package com.orka.myfinances.fixtures.resources.models.receive

import com.orka.myfinances.data.models.receive.ReceiveItem
import com.orka.myfinances.fixtures.resources.amount
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2

val receiveItem1 = ReceiveItem(
    id = id1,
    product = product1,
    amount = amount,
)
val receiveItem2 = ReceiveItem(
    id = id2,
    product = product2,
    amount = amount,
)

val receiveItems = listOf(receiveItem1, receiveItem2)