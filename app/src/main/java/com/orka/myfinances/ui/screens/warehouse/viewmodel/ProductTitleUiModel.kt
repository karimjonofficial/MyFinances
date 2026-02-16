package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.ui.screens.home.components.ProductTitleCardModel

data class ProductTitleUiModel(
    val model: ProductTitleCardModel,
    val title: ProductTitle
)