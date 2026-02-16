package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.home.components.ProductTitleCardModel
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCardModel

fun ProductTitle.toModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = if(description != null) UiText.Str(description) else UiText.Res(R.string.no_description_provided)
    )
}

fun ProductTitle.toUiModel(): ProductTitleUiModel {
    return ProductTitleUiModel(
        model = toModel(),
        title = this
    )
}

fun StockItem.toModel(): StockItemCardModel {
    return StockItemCardModel(
        title = product.title.name,
        price = product.price.toString(),
        amount = amount.toString()
    )
}

fun StockItem.toUiModel(): StockItemUiModel {
    return StockItemUiModel(
        model = toModel(),
        item = this
    )
}