package com.orka.myfinances.ui.map

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.ui.models.card.StockItemCardModel
import com.orka.myfinances.ui.screens.stock.StockItemUiModel

fun List<StockItem>.toMap(): Map<String, List<StockItemUiModel>> {
    val map = sortedBy { it.product.title.name }
        .groupBy { it.product.title.name.stickyHeaderKey() }
        .mapValues { (_, stockItems) ->
            stockItems.map { it.toUiModel(FormatPriceImpl(), FormatDecimalImpl()) }
        }

    return map
}

fun StockItem.toCardModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): StockItemCardModel {
    val properties = product.title.properties
        .takeIf { it.isNotEmpty() }
        ?.joinToString { "${it.field.name}: ${it.value}" }

    return StockItemCardModel(
        title = product.title.name,
        price = formatPrice.formatPrice(product.price.toDouble()),
        amount = formatDecimal.formatDecimal(amount.toDouble()),
        properties = properties,
        description = description,
        increaseEnabled = true
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
        salePrice = formatPrice.formatPrice(product.price.toDouble()),
        amount = amount
    )
}