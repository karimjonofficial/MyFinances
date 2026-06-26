package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.models.Id

fun interface SetNotified {
    suspend fun setNotified(id: Id, notified: Boolean): Boolean
}
