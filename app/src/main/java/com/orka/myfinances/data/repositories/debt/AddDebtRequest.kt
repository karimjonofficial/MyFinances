package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.models.Id
import kotlin.time.Instant

data class AddDebtRequest(
    val clientId: Id,
    val price: Int,
    val description: String?,
    val endDateTime: Instant? = null
)
