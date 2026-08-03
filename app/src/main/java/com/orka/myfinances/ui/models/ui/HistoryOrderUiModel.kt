package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.HistoryOrderCardModel

data class HistoryOrderUiModel(
    val id: Id,
    val model: HistoryOrderCardModel
)