package com.orka.myfinances.models

data class Company(
    val id: Id,
    val name: String,
    val address: String? = null,
    val phone: String? = null
)
