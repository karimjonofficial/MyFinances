package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
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

fun StockItem.toModel(
    priceFormatter: FormatPrice,
    decimalFormatter: FormatDecimal
): StockItemCardModel {
    return StockItemCardModel(
        title = product.title.name,
        price = priceFormatter.formatPrice(product.price.toDouble()),
        amount = decimalFormatter.formatDecimal(amount.toDouble())
    )
}

fun StockItem.toUiModel(
    priceFormatter: FormatPrice,
    decimalFormatter: FormatDecimal
): StockItemUiModel {
    return StockItemUiModel(
        model = toModel(priceFormatter, decimalFormatter),
        item = this
    )
}
