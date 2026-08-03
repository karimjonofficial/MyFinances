package com.orka.myfinances.application.viewmodels.stock

import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.card.StockItemCardModel
import com.orka.myfinances.ui.models.ui.StockItemUiModel

fun StockItemDto.toCardModel(
    price: Long,
    basketAmount: Int? = null
): StockItemCardModel {
    val properties = product.title.properties?.joinToString { "${it.field.name}: ${it.value}" }

    return StockItemCardModel(
        title = product.title.name,
        price = price.toInt(),
        amount = amount,
        properties = properties,
        description = product.title.description,
        basketAmount = basketAmount,
        increaseEnabled = if(basketAmount != null) basketAmount < amount else false
    )
}

fun StockItemDto.toUiModel(
    basketAmount: Int? = null
): StockItemUiModel {
    return StockItemUiModel(
        id = Id(product.id),
        salePrice = product.salePrice.toInt(),
        exposedPrice = product.exposedPrice.toInt(),
        model = toCardModel(product.exposedPrice, basketAmount),
        amount = amount
    )
}

fun StockItemUiModel.toExposed(): StockItemUiModel {
    return copy(model = model.copy(price = salePrice))
}

fun StockItemUiModel.toHidden(): StockItemUiModel {
    return copy(model = model.copy(price = exposedPrice))
}

fun State.Success<ChunkUiModel<StockItemUiModel>>.toExposed(): State<ChunkUiModel<StockItemUiModel>> {
    return State.Success(value.copy(content = value.content.mapValues { it.value.map { model -> model.toExposed() } }))
}

fun State.Success<ChunkUiModel<StockItemUiModel>>.toHidden(): State<ChunkUiModel<StockItemUiModel>> {
    return State.Success(value.copy(content = value.content.mapValues { it.value.map { model -> model.toHidden() } }))
}
