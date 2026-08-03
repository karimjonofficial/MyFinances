package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.BasketItemCardModel

data class BasketItemUiModel(
    val productId: Id,
    val amount: Int,
    val model: BasketItemCardModel
)