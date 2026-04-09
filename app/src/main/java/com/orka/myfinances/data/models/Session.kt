package com.orka.myfinances.data.models

data class Session(
    val credentials: Credentials,
    val officeId: Id,
    val companyId: Id
)