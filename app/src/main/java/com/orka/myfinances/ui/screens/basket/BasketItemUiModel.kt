package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.basket.components.BasketItemCardModel

data class BasketItemUiModel(
    val productId: Id,
    val amount: Int,
    val model: BasketItemCardModel
)