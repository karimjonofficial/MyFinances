package com.orka.myfinances.data.models

data class Client(
    val id: Id,
    val firstName: String,
    val lastName: String = "",
    val phone: String = "",
    val address: String = ""
)