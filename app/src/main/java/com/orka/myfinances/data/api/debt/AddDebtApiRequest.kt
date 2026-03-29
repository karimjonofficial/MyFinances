package com.orka.myfinances.data.api.debt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class AddDebtApiRequest(
    @SerialName("client") val clientId: Int,
    @SerialName("branch") val officeId: Int,
    val price: Int,
    val description: String?,
    @SerialName("end_date_time") val endDateTime: Instant? = null
)