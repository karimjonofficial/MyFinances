package com.orka.myfinances.data.api.receive.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddReceiveApiRequest(
    val items: List<AddReceiveApiRequestItem>,
    @SerialName("branch") val branchId: Int,
    val price: Int,
    val comment: String? = null
)