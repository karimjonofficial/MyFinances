package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.data.repositories.notification.ReadNotification
import com.orka.myfinances.format.FormatLocalDate
import com.orka.myfinances.format.FormatTime
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.MapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.screens.notifications.NotificationUiModel
import com.orka.myfinances.ui.screens.notifications.NotificationsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class NotificationsScreenViewModel(
    getChunk: GetChunk<NotificationDto>,
    private val readNotification: ReadNotification,
    private val formatLocalDate: FormatLocalDate,
    private val formatTime: FormatTime,
    logger: Logger,
) : MapChunkViewModel<NotificationDto, NotificationUiModel>(
    get = getChunk,
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map = chunk.results
            .groupBy { it.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { formatLocalDate.formatLocalDate(it.key) }
            .mapValues { it.value.map { model -> model.toUiModel(formatTime) } }

        ChunkUiModel(
            size = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), NotificationsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun read(notification: NotificationUiModel) {
        tryTransition { oldState ->
            if (readNotification.read(notification.id)) {
                refresh()
                oldState
            } else State.Failure(CouldNotRead, oldState.value)
        }
    }
}