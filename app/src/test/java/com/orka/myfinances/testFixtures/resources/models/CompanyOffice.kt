package com.orka.myfinances.testFixtures.resources.models

import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.testFixtures.resources.address
import com.orka.myfinances.testFixtures.resources.name
import com.orka.myfinances.testFixtures.resources.phone

val companyOffice = CompanyOffice(
    id = id1,
    name = name,
    company = company,
    templates = emptyList(),
    address = address,
    phone = phone
)