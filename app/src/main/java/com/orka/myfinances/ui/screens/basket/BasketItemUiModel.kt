package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.ui.screens.basket.components.BasketItemCardModel

data class BasketItemUiModel(
    val item: BasketItem,
    val model: BasketItemCardModel
)