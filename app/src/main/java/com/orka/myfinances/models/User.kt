package com.orka.myfinances.models

data class User(
    val id: Id,
    val firstName: String,
    val username: String,
    val company: Company,
    val lastName: String? = null,
    val patronymic: String? = null,
    val phone: String? = null,
    val address: String? = null,
)