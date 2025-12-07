package com.orka.myfinances.data.repositories.client

data class AddClientRequest(
    val name: String,
    val lastName: String?,
    val phone: String?,
    val address: String?
)