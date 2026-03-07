package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.history.components.ReceiveCardModel
import kotlin.time.Instant

data class ReceiveUiModel(
    val model: ReceiveCardModel,
    val instant: Instant,
    val id: Id
)