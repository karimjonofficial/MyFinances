package com.orka.myfinances.data.repositories.notification

import com.orka.myfinances.data.api.notification.NotificationApiModel
import com.orka.myfinances.data.dtos.notification.NotificationDto

fun NotificationApiModel.toDto(): NotificationDto {
    return NotificationDto(
        id = id,
        title = title,
        message = message,
        read = read,
        dateTime = dateTime
    )
}
