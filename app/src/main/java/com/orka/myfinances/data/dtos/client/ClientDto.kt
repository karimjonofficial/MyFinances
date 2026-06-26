package com.orka.myfinances.data.dtos.client

data class ClientDto(
    val id: Int,
    val firstName: String,
    val lastName: String?,
    val patronymic: String?,
    val phone: String?,
    val address: String?
)
