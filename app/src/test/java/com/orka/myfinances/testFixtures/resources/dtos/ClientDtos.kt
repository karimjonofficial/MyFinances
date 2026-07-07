package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.client.ClientDto

val clientDto1 = ClientDto(
    id = 1,
    firstName = "Jane",
    lastName = "Smith",
    patronymic = null,
    phone = "987654321",
    address = "Oak St 2"
)

val clientDtos = listOf(clientDto1)
