package com.orka.myfinances.ui.screens.host

import kotlinx.serialization.Serializable

@Serializable
data class CompanyModel(
    val id: Int,
    val name: String,
    val phone: String?,
    val address: String?
)
