package com.orka.myfinances.data.api.sale.models.request

import com.orka.myfinances.lib.data.models.Item
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddSaleApiRequest(
    @SerialName("client") val clientId: Int,
    val items: List<Item>,
    val price: Int,
    @SerialName("branch") val officeId: Int,
    val description: String? = null
)