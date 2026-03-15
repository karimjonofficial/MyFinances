package com.orka.myfinances.data.api.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddClientApiRequest(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String?,
    @SerialName("patronymic") val patronymic: String?,
    @SerialName("company") val companyId: Int,
    val phone: String?,
    val address: String?
)