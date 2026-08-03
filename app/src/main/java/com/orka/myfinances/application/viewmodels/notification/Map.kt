package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.NotificationCardModel
import com.orka.myfinances.ui.models.ui.NotificationUiModel

fun NotificationDto.toUiModel(): NotificationUiModel {
    return NotificationUiModel(
        id = Id(id),
        model = toCardModel()
    )
}

fun NotificationDto.toCardModel(): NotificationCardModel {
    return NotificationCardModel(
        title = title,
        message = message,
        read = read,
        dateTime = dateTime
    )
}
