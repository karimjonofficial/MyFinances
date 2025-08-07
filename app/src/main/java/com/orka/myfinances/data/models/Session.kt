package com.orka.myfinances.data.models

data class Session(
    val user: User,
    val credential: Credential,
    val companyOffice: CompanyOffice
)