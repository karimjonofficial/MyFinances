package com.orka.myfinances.data.api.order.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class SetEndDateApiRequest(
    val id: Int,
    @SerialName("end_date_time") val endDateTime: Instant
)
