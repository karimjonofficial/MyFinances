package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.NotificationCardModel

data class NotificationUiModel(
    val id: Id,
    val model: NotificationCardModel
)