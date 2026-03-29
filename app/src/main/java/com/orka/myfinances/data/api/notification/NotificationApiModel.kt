package com.orka.myfinances.data.api.notification

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class NotificationApiModel(
    val id: Int,
    val title: String,
    val message: String,
    val read: Boolean,
    val dateTime: Instant
)