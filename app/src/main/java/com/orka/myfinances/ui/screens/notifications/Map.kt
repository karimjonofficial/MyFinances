package com.orka.myfinances.ui.screens.notifications

import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.lib.ui.models.ChunkMapState

fun List<Notification>.toChunkMapState(): ChunkMapState<NotificationUiModel> {
    return ChunkMapState(
        count = size,
        pageIndex = 0,
        nextPageIndex = 1,
        previousPageIndex = 0,
        content = groupBy { it.dateTime }
            .mapKeys { it.key.toString() }
            .mapValues { entry -> entry.value.map { it.toUiModel()} }
    )
}

fun Notification.toUiModel(): NotificationUiModel {
    return NotificationUiModel(
        id = id,
        model = toCardModel()
    )
}

fun Notification.toCardModel(): NotificationCardModel {
    return NotificationCardModel(
        title = title,
        message = message,
        read = read,
        time = dateTime.toString()
    )
}