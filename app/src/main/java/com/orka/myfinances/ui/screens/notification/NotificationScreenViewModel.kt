package com.orka.myfinances.ui.screens.notification

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.lib.ui.models.Text
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class NotificationScreenViewModel(
    private val repository: NotificationRepository,
    logger: Logger,
    loading: Text,
    failure: Text
) : ListViewModel<Notification>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
) {
    fun read(notification: Notification) = launch {
        if(!notification.read) {
            repository.read(notification.id)
            initialize()
        }
    }
}
