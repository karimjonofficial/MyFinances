package com.orka.myfinances.application.viewmodels.stock

import com.orka.myfinances.data.api.stock.models.StockItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.stock.StockItemCardModel
import com.orka.myfinances.ui.screens.stock.StockItemUiModel

fun StockItemApiModel.toCardModel(
    price: String,
    formatDecimal: FormatDecimal,
    basketAmount: String? = null
): StockItemCardModel {
    return StockItemCardModel(
        title = product.title.name,
        price = price,
        amount = formatDecimal.formatDecimal(amount.toDouble()),
        basketAmount = basketAmount
    )
}

fun StockItemApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    basketAmount: String? = null
): StockItemUiModel {
    val price = formatPrice.formatPrice(product.salePrice.toDouble())
    val exposedPrice = formatPrice.formatPrice(product.exposedPrice.toDouble())

    return StockItemUiModel(
        id = Id(product.id),
        salePrice = price,
        exposedPrice = exposedPrice,
        model = toCardModel(exposedPrice, formatDecimal, basketAmount),
    )
}

fun StockItemUiModel.toExposed(): StockItemUiModel {
    return copy(model = model.copy(price = salePrice))
}

fun StockItemUiModel.toHidden(): StockItemUiModel {
    return copy(model = model.copy(price = exposedPrice))
}

fun State.Success<ChunkMapState<StockItemUiModel>>.toExposed(): State<ChunkMapState<StockItemUiModel>> {
    return State.Success(value.copy(content = value.content.mapValues { it.value.map { model -> model.toExposed() } }))
}

fun State.Success<ChunkMapState<StockItemUiModel>>.toHidden(): State<ChunkMapState<StockItemUiModel>> {
    return State.Success(value.copy(content = value.content.mapValues { it.value.map { model -> model.toHidden() } }))
}