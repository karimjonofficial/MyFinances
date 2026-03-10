package com.orka.myfinances.data.api.receive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddReceiveApiRequest(
    val items: List<AddReceiveApiRequestItem>,
    @SerialName("branch") val officeId: Int,
    val price: Int,
    val comment: String? = null
)