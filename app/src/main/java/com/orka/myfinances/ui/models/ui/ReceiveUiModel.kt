package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ReceiveCardModel
import kotlin.time.Instant

data class ReceiveUiModel(
    val model: ReceiveCardModel,
    val dateTime: Instant,
    val id: Id
)