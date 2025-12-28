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

val sale1 = Sale(id1, user1, client1, saleItems, price, dateTime, description)
val sale2 = Sale(id2, user1, client2, saleItems, price, dateTime, description)

val sales = listOf(sale1, sale2)