package com.orka.myfinances.data.api.order.models.response

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.api.user.UserApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class OrderApiModel(
    val id: Int,
    val user: UserApiModel,
    val client: ClientApiModel,
    val branch: OfficeApiModel,
    val price: Long,
    @SerialName("end_date_time") val endDateTime: Instant? = null,
    val completed: Boolean,
    @SerialName("completed_date_time") val completedDateTime: Instant?,
    val description: String? = null,
    val items: List<OrderItemApiModel>,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)