package com.orka.myfinances.ui.screens.notifications

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.NotificationUiModel

interface NotificationsScreenInteractor : Refreshable, PaginatedSearchable {
    fun read(notification: NotificationUiModel)

    companion object {
        val dummy = object : NotificationsScreenInteractor {
            override fun read(notification: NotificationUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
