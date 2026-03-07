package com.orka.myfinances.data.api.receive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class ReceiveApiModel(
    val id: Int,
    val user: String,
    val branch: String,
    val price: Long,
    @SerialName("created_at") val dateTime: Instant,
    val items: List<ReceiveItemApiModel>,
    val description: String? = null,
)