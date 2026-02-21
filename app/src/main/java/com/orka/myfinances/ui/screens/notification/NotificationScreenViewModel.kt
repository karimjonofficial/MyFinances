package com.orka.myfinances.ui.screens.notification

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.ListViewModel
import kotlinx.coroutines.flow.asStateFlow

typealias IListViewModel = com.orka.myfinances.lib.ui.viewmodel.ListViewModel<Notification>

class NotificationScreenViewModel(
    private val repository: NotificationRepository,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : ListViewModel<Notification>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
), IListViewModel {
    override val uiState = state.asStateFlow()

    fun read(notification: Notification) = launch {
        if(!notification.read) {
            repository.read(notification.id)
            initialize()
        }
    }
}
