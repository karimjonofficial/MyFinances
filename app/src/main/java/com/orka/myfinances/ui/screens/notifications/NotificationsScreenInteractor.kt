package com.orka.myfinances.ui.screens.notifications

import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface NotificationsScreenInteractor : ListViewModel<Notification> {
    fun read(notification: Notification)

    companion object {
        val dummy = object : NotificationsScreenInteractor {
            override val uiState: StateFlow<State> = MutableStateFlow(State.Initial)
            override fun read(notification: Notification) {}
            override fun initialize() {}
        }
    }
}