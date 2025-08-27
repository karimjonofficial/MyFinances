package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.fixtures.resources.models.template.templates

val companyOffice1 = CompanyOffice(
    id = id1,
    name = "CompanyOffice 1",
    company = company1,
    templates = templates,
    address = "Address of the CompanyOffice 1",
    phone = "Phone of the CompanyOffice 1"
)