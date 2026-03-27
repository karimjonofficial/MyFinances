package com.orka.myfinances.ui.screens.notifications

import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface NotificationsScreenInteractor : StateFul {
    fun read(notification: Notification)

    companion object {
        val dummy = object : NotificationsScreenInteractor {
            override fun read(notification: Notification) {}
            override fun refresh() {}
        }
    }
}