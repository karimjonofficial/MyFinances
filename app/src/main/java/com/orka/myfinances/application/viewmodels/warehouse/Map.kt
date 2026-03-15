package com.orka.myfinances.application.viewmodels.warehouse

import com.orka.myfinances.R
import com.orka.myfinances.data.api.stock.StockItemApiModel
import com.orka.myfinances.data.api.title.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.home.components.ProductTitleCardModel
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCardModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductTitleUiModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockItemUiModel

fun ProductTitleApiModel.toCardModel(): ProductTitleCardModel {
    return ProductTitleCardModel(
        title = name,
        description = if(description != null) UiText.Str(description) else UiText.Res(R.string.no_description_provided)
    )
}

fun ProductTitleApiModel.toUiModel(): ProductTitleUiModel {
    return ProductTitleUiModel(
        model = toCardModel(),
        id = Id(id)
    )
}

fun StockItemApiModel.toCardModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): StockItemCardModel {
    return StockItemCardModel(
        title = product.title.name,
        price = formatPrice.formatPrice(product.price.toDouble()),
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}

fun StockItemApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): StockItemUiModel {
    return StockItemUiModel(
        model = toCardModel(formatPrice, formatDecimal),
        id = Id(product.id)
    )
}
