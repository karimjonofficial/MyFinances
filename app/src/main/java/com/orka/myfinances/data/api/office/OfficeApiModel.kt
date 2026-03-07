package com.orka.myfinances.data.api.office

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfficeApiModel(
    val id: Int,
    @SerialName("company") val companyId: Int,
    val name: String,
    val address: String,
    val phone: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("modified_at") val modifiedAt: String
)