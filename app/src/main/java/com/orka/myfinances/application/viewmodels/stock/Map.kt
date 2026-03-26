package com.orka.myfinances.application.viewmodels.stock

import com.orka.myfinances.data.api.stock.StockItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.stock.StockItemCardModel
import com.orka.myfinances.ui.screens.stock.StockItemUiModel

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
