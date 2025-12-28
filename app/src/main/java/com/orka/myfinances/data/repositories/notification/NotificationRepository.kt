package com.orka.myfinances.data.repositories.notification

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.fixtures.resources.models.notifications
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.delay

class NotificationRepository : MockGetRepository<Notification>(notifications) {

    suspend fun read(id: Id) {
        delay(duration)
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            val oldNotification = items[index]
            items[index] = oldNotification.copy(read = true)
        }
    }
}