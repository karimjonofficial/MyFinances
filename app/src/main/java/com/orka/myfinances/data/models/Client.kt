package com.orka.myfinances.data.models

data class Client(
    val id: Id,
    val firstName: String,
    val lastName: String? = null,
    val phone: String? = null,
    val address: String? = null
)