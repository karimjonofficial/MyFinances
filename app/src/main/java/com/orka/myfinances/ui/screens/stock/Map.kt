package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice

fun List<StockItem>.toMap(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): Map<String, List<StockItemUiModel>> {
    val map = sortedBy { it.product.title.name }
        .groupBy { it.product.title.name.stickyHeaderKey() }
        .mapValues { (_, stockItems) ->
            stockItems.map { it.toUiModel(formatPrice, formatDecimal) }
        }

    return map
}

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