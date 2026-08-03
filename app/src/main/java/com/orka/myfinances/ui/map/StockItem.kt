package com.orka.myfinances.ui.map

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.ui.models.card.StockItemCardModel
import com.orka.myfinances.ui.models.ui.StockItemUiModel

fun List<StockItem>.toMap(): Map<String, List<StockItemUiModel>> {
    val map = sortedBy { it.product.title.name }
        .groupBy { it.product.title.name.stickyHeaderKey() }
        .mapValues { (_, stockItems) ->
            stockItems.map { it.toUiModel() }
        }

    return map
}

fun StockItem.toCardModel(): StockItemCardModel {
    val properties = product.title.properties
        .takeIf { it.isNotEmpty() }
        ?.joinToString { "${it.field.name}: ${it.value}" }

    return StockItemCardModel(
        title = product.title.name,
        price = product.price,
        amount = amount,
        properties = properties,
        description = product.title.description,
        increaseEnabled = true
    )
}

fun StockItem.toUiModel(): StockItemUiModel {
    return StockItemUiModel(
        model = toCardModel(),
        id = this.product.id,
        exposedPrice = product.exposedPrice,
        salePrice = product.price,
        amount = amount
    )
}