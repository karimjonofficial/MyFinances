package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText

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
    val properties = product.title.properties.joinToString { "${it.field.name}: ${it.value}" }

    return StockItemCardModel(
        title = product.title.name,
        price = formatPrice.formatPrice(product.price.toDouble()),
        amount = formatDecimal.formatDecimal(amount.toDouble()),
        properties = UiText.Str(properties),
        description = UiText.Str(product.title.description ?: "")
    )
}

fun StockItem.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): StockItemUiModel {
    return StockItemUiModel(
        model = toCardModel(formatPrice, formatDecimal),
        id = this.product.id,
        exposedPrice = formatPrice.formatPrice(product.exposedPrice.toDouble()),
        salePrice = formatPrice.formatPrice(product.price.toDouble())
    )
}