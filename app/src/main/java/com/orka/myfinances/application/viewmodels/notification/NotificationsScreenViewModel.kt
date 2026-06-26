package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.screens.notifications.NotificationUiModel
import com.orka.myfinances.ui.screens.notifications.NotificationsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class NotificationsScreenViewModel(
    private val repository: NotificationRepository,
    private val formatLocalDate: FormatLocalDate,
    private val formatTime: FormatTime,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : MapChunkViewModel<NotificationDto, NotificationUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page, query -> repository.getChunk(size, page, query) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map = chunk.results
            .groupBy { it.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { formatLocalDate.formatLocalDate(it.key) }
            .mapValues { it.value.map { model -> model.toUiModel(formatTime) } }

        ChunkUiModel(
            count = chunk.count,
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
            if (repository.read(notification.id)) {
                refresh()
                oldState
            } else State.Failure(failure, oldState.value)
        }
    }
}
