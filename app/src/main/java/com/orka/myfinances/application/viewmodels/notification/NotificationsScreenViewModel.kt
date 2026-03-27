package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.notification.NotificationApi
import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.ListViewModel
import com.orka.myfinances.ui.screens.notifications.NotificationsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class NotificationsScreenViewModel(
    private val notificationApi: NotificationApi,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : ListViewModel<Notification>(
    loading = loading,
    failure = failure,
    get = { notificationApi.getAll() },
    logger = logger
), NotificationsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun read(notification: Notification) {
        launch {
            if (!notification.read && notificationApi.read(notification.id.value)) {
                initialize()
            }
        }
    }
}