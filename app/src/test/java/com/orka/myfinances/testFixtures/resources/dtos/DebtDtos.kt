package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.testFixtures.resources.dateTime

val debtDto1 = DebtDto(
    id = 1,
    user = userDto1,
    client = clientDto1,
    completed = false,
    price = 100000,
    notified = false,
    dateTime = dateTime,
    endDateTime = null,
    description = "Test Debt 1"
)

val debtDto2 = DebtDto(
    id = 2,
    user = userDto1,
    client = clientDto1,
    completed = true,
    price = 50000,
    notified = true,
    dateTime = dateTime,
    endDateTime = null,
    description = "Test Debt 2"
)

val debtDtos = listOf(debtDto1, debtDto2)
