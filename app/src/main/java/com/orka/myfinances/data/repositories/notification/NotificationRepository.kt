package com.orka.myfinances.data.repositories.notification

import com.orka.myfinances.data.api.notification.NotificationApi
import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.data.repositories.Chunk

class NotificationRepository(
    private val api: NotificationApi
) : GetChunk<NotificationDto>, ReadNotification {
    override suspend fun getChunk(
        size: Int,
        page: Int
    ): Chunk<NotificationDto>? {
        return api.getChunk(page, size)?.toChunk { it.toDto() }
    }

    override suspend fun read(id: Id): Boolean {
        return api.read(id.value)
    }
}
