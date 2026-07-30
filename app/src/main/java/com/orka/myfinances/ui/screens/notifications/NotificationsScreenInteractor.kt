package com.orka.myfinances.ui.screens.notifications

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.Refreshable

interface NotificationsScreenInteractor : Refreshable, ChunkViewModel {
    fun read(notification: NotificationUiModel)

    companion object {
        val dummy = object : NotificationsScreenInteractor {
            override fun read(notification: NotificationUiModel) {}
            override fun initialize() {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
        }
    }
}