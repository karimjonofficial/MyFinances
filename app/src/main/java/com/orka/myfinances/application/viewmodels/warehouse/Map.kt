package com.orka.myfinances.application.viewmodels.warehouse

import com.orka.myfinances.R
import com.orka.myfinances.data.api.stock.StockItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.home.components.ProductTitleCardModel
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCardModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductTitleUiModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockItemUiModel

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

fun StockItemApiModel.toModel(
    priceFormatter: FormatPrice,
    decimalFormatter: FormatDecimal
): StockItemCardModel {
    return StockItemCardModel(
        title = product.title.name,
        price = priceFormatter.formatPrice(product.price.toDouble()),
        amount = decimalFormatter.formatDecimal(amount.toDouble())
    )
}

fun StockItemApiModel.toUiModel(
    priceFormatter: FormatPrice,
    decimalFormatter: FormatDecimal
): StockItemUiModel {
    return StockItemUiModel(
        model = toModel(priceFormatter, decimalFormatter),
        id = Id(product.id)
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
        id = this.product.id
    )
}
