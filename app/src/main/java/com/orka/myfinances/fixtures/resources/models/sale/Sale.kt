package com.orka.myfinances.fixtures.resources.models.sale

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.fixtures.resources.models.client2
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.fixtures.resources.price

val sale1 = Sale(
    id = id1,
    user = user1,
    client = client1,
    items = saleItems,
    price = price,
    dateTime = dateTime,
    description = "Client requested gift wrapping for the briefcase. Discount code 'LOYALTY10' applied at checkout. Delivery scheduled for priority courier on Monday morning."
)
val sale2 = Sale(
    id = id2,
    user = user1,
    client = client2,
    items = saleItems,
    price = price,
    dateTime = dateTime,
    description = description
)

val sales = listOf(sale1, sale2)