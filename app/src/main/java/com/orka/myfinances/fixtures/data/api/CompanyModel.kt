package com.orka.myfinances.fixtures.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CompanyModel(
    val id: Int,
    val name: String,
    val address: String? = null,
    val phone: String? = null
)