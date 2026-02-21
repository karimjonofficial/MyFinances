package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Client

val client1 = Client(
    id = id1,
    firstName = "John",
    lastName = "Doe",
    address = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    phone = "The phone number of user 1"
)
val client2 = Client(
    id = id2,
    firstName = "John",
    lastName = "Doe",
    address = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    phone = "The phone number of user2"
)

val clients = listOf(client1, client2)