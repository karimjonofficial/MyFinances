package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.data.api.notification.NotificationApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.notifications.NotificationCardModel
import com.orka.myfinances.ui.screens.notifications.NotificationUiModel

fun NotificationApiModel.toUiModel(formatTime: FormatTime): NotificationUiModel {
    return NotificationUiModel(
        id = Id(id),
        model = toCardModel(formatTime)
    )
}

fun NotificationApiModel.toCardModel(formatTime: FormatTime): NotificationCardModel {
    return NotificationCardModel(
        title = title,
        message = message,
        read = read,
        time = formatTime.formatTime(dateTime)
    )
}