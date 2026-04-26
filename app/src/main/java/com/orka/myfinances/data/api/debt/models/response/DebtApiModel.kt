package com.orka.myfinances.data.api.debt.models.response

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.api.user.UserApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class DebtApiModel(
    val id: Int,
    val user: UserApiModel,
    val client: ClientApiModel,
    @SerialName("is_completed") val completed: Boolean,
    val price: Long,
    val notified: Boolean,
    @SerialName("created_at") val dateTime: Instant,
    @SerialName("end_date_time") val endDateTime: Instant? = null,
    val description: String? = null
)