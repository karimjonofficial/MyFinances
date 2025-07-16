package com.orka.myfinances.models

data class Session(
    val user: User,
    val credential: Credential,
    val companyOffice: CompanyOffice
)