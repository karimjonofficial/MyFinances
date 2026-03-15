package com.orka.myfinances.ui.screens.warehouse

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCardModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockItemUiModel

fun StockItem.toCardModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): StockItemCardModel {
    return StockItemCardModel(
        title = product.title.name,
        price = formatPrice.formatPrice(product.price.toDouble()),
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}

fun StockItem.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): StockItemUiModel {
    return StockItemUiModel(
        model = toCardModel(formatPrice, formatDecimal),
        id = this.product.id
    )
}