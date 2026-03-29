package com.orka.myfinances.ui.screens.notifications

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface NotificationsScreenInteractor : StateFul, ChunkViewModel {
    fun read(notification: NotificationUiModel)

    companion object {
        val dummy = object : NotificationsScreenInteractor {
            override fun read(notification: NotificationUiModel) {}
            override fun initialize() {}
            override fun refresh() {}
            override fun loadMore() {}
        }
    }
}