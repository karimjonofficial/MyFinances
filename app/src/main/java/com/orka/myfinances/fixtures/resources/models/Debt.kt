package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.description

val debt1 = Debt(
    id = id1,
    user = user1,
    client = client1,
    price = 100,
    notified = false,
    dateTime = dateTime,
    endDateTime = dateTime,
    description = description
)
val debt2 = Debt(
    id = id2,
    user = user1,
    client = client2,
    price = 200,
    notified = true,
    dateTime = dateTime,
    endDateTime = dateTime,
    description = description
)

val debts = listOf(debt1, debt2)
