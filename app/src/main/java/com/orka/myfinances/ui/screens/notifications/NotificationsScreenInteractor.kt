package com.orka.myfinances.ui.screens.notifications

import com.orka.myfinances.data.models.Notification

interface NotificationsScreenInteractor {
    fun read(notification: Notification)
}