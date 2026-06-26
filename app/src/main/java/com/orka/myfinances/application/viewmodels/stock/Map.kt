package com.orka.myfinances.application.viewmodels.stock

import com.orka.myfinances.R
import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.stock.StockItemCardModel
import com.orka.myfinances.ui.screens.stock.StockItemUiModel

fun StockItemDto.toCardModel(
    price: Long,
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice,
    basketAmount: Int? = null
): StockItemCardModel {
    val properties = product.title.properties.joinToString { "${it.field.name}: ${it.value}" }

    return StockItemCardModel(
        title = product.title.name,
        price = formatPrice.formatPrice(price.toDouble()),
        amount = formatDecimal.formatDecimal(amount.toDouble()),
        properties = UiText.Str(properties),
        description = if(!product.title.description.isNullOrBlank()) UiText.Str(product.title.description) else UiText.Res(R.string.no_description_provided),
        basketAmount = if(basketAmount != null) formatDecimal.formatDecimal(basketAmount.toDouble()) else null,
        increaseEnabled = if(basketAmount != null) basketAmount < amount else false
    )
}

fun StockItemDto.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    basketAmount: Int? = null
): StockItemUiModel {
    val price = formatPrice.formatPrice(product.salePrice.toDouble())
    val exposedPrice = formatPrice.formatPrice(product.exposedPrice.toDouble())

    return StockItemUiModel(
        id = Id(product.id),
        salePrice = price,
        exposedPrice = exposedPrice,
        model = toCardModel(product.exposedPrice, formatDecimal, formatPrice, basketAmount),
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
