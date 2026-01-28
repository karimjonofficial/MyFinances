package com.orka.myfinances.data.models

data class User(
    val id: Id,
    val firstName: String,
    val userName: String,
    val company: Company,
    val lastName: String? = null,
    val patronymic: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val profession: String? = null
)