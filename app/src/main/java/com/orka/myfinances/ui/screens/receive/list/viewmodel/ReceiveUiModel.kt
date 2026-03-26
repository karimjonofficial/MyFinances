package com.orka.myfinances.ui.screens.receive.list.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.receive.list.components.ReceiveCardModel
import kotlin.time.Instant

data class ReceiveUiModel(
    val model: ReceiveCardModel,
    val instant: Instant,
    val id: Id
)