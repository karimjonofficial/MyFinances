package com.orka.myfinances.ui.screens.notifications

data class NotificationCardModel(
    val title: String,
    val message: String,
    val read: Boolean,
    val time: String
)