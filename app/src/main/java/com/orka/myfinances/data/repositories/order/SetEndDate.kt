package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.models.Id
import kotlin.time.Instant

fun interface SetEndDate {
    suspend fun setEndDate(id: Id, endDateTime: Instant): Boolean
}
