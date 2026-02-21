package com.orka.myfinances.ui.screens.home.viewmodel.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.ui.screens.home.components.BasketItemCardModel

data class BasketItemUiModel(
    val item: BasketItem,
    val model: BasketItemCardModel
)