package com.orka.myfinances.ui.screens.clients

data class AddClientRequest(
    val name: String,
    val lastName: String?,
    val phone: String?,
    val address: String?
)