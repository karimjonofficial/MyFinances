package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.TemplateCardModel

data class TemplateUiModel(
    val model: TemplateCardModel,
    val id: Id
)