package com.orka.myfinances.data.models

data class Office(
    val id: Id,
    val name: String,
    val company: Company,
    val address: String? = null,
    val phone: String? = null
)