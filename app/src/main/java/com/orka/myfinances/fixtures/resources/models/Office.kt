package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Office

val office1 = Office(
    id = id1,
    name = "Office 1",
    company = company1,
    address = "Address of the Office 1",
    phone = "Phone of the Office 1"
)
val office2 = Office(
    id = id2,
    name = "Office 2",
    company = company1,
    address = "Address of the Office 2",
    phone = "Phone of the Office 2"
)

val offices = listOf(office1, office2)