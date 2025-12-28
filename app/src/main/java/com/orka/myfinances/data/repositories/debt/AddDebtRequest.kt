package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.models.Client
import kotlin.time.Instant

data class AddDebtRequest(
    val client: Client,
    val price: Int,
    val description: String?,
    val endDateTime: Instant
)
