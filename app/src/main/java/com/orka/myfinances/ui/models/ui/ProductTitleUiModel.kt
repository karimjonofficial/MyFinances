package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ProductTitleCardModel

data class ProductTitleUiModel(
    val model: ProductTitleCardModel,
    val id: Id
)