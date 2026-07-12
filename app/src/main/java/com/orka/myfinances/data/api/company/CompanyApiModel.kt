package com.orka.myfinances.data.api.company

import kotlinx.serialization.Serializable

@Serializable
data class CompanyApiModel(
    val id: Int,
    val name: String,
    val phone: String?,
    val address: String?
)