package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Office

val office1 = Office(
    id = id1,
    name = "CompanyOffice 1",
    company = company1,
    address = "Address of the CompanyOffice 1",
    phone = "Phone of the CompanyOffice 1"
)
val office2 = Office(
    id = id2,
    name = "CompanyOffice 2",
    company = company1,
    address = "Address of the CompanyOffice 2",
    phone = "Phone of the CompanyOffice 2"
)

val offices = listOf(office1, office2)