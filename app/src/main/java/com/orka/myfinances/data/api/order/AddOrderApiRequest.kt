package com.orka.myfinances.data.api.order

import com.orka.myfinances.lib.data.models.Item
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class AddOrderApiRequest(
    @SerialName("client") val clientId: Int,
    @SerialName("branch") val branchId: Int,
    val items: List<Item>,
    val price: Int,
    @SerialName("end_date_time") val endDateTime: Instant? = null,
    val description: String? = null
)