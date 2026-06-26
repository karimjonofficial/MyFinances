package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.notifications.NotificationCardModel
import com.orka.myfinances.ui.screens.notifications.NotificationUiModel

fun NotificationDto.toUiModel(formatTime: FormatTime): NotificationUiModel {
    return NotificationUiModel(
        id = Id(id),
        model = toCardModel(formatTime)
    )
}

fun NotificationDto.toCardModel(formatTime: FormatTime): NotificationCardModel {
    return NotificationCardModel(
        title = title,
        message = message,
        read = read,
        time = formatTime.formatTime(dateTime)
    )
}
