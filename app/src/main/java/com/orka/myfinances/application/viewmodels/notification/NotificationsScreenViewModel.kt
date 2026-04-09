package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.data.api.notification.NotificationApi
import com.orka.myfinances.data.api.notification.NotificationApiModel
import com.orka.myfinances.data.api.notification.read
import com.orka.myfinances.lib.data.api.getChunk
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.screens.notifications.NotificationUiModel
import com.orka.myfinances.ui.screens.notifications.NotificationsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class NotificationsScreenViewModel(
    private val api: NotificationApi,
    private val formatLocalDate: FormatLocalDate,
    private val formatTime: FormatTime,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : MapChunkViewModel<NotificationApiModel, NotificationUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> api.getChunk(size, page) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map = chunk.results
            .groupBy { it.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { formatLocalDate.formatLocalDate(it.key) }
            .mapValues { it.value.map { model -> model.toUiModel(formatTime) } }

        ChunkMapState(
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
        launch {
            try {
                if (api.read(notification.id)) {
                    refresh()
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}