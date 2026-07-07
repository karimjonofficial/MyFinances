package com.orka.myfinances.data.models

data class Session(
    val credentials: Credentials,
    val branchId: Id,
    val companyId: Id
)