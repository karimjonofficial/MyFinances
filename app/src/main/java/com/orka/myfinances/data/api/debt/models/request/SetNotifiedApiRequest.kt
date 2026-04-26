package com.orka.myfinances.data.api.debt.models.request

import kotlinx.serialization.Serializable

@Serializable
data class SetNotifiedApiRequest(
    val notified: Boolean
)