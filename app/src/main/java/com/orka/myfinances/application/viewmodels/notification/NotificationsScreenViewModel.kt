package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.data.repositories.notification.ReadNotification
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.ui.NotificationUiModel
import com.orka.myfinances.ui.statuses.failure.CouldNotRead
import com.orka.myfinances.ui.screens.notifications.NotificationsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class NotificationsScreenViewModel(
    getChunk: GetChunk<NotificationDto>,
    searchChunk: SearchChunk<NotificationDto>,
    private val readNotification: ReadNotification,
    logger: Logger,
) : SearchableMapChunkViewModel<NotificationDto, NotificationUiModel>(
    get = getChunk,
    search = searchChunk,
    map = { it.toUiModel() },
    groupBy = { it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString() },
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