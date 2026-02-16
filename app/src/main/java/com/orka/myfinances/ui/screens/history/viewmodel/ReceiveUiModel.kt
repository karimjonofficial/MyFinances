package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.ui.screens.history.components.ReceiveCardModel

data class ReceiveUiModel(
    val model: ReceiveCardModel,
    val receive: Receive
)