package com.orka.myfinances.data.repositories.client

data class AddClientRequest(
    val firstName: String,
    val lastName: String?,
    val patronymic: String?,
    val phone: String?,
    val address: String?
)
