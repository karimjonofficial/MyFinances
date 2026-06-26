package com.orka.myfinances.data.repositories.notification

import com.orka.myfinances.data.models.Id

fun interface ReadNotification {
    suspend fun read(id: Id): Boolean
}
