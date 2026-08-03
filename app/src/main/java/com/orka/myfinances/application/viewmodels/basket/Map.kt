package com.orka.myfinances.application.viewmodels.basket

import com.orka.myfinances.R
import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.MinBasketItem
import com.orka.myfinances.ui.models.ui.BasketItemUiModel
import com.orka.myfinances.ui.models.card.BasketItemCardModel

fun BasketItem.toModel(): BasketItemCardModel {
    val propertiesText = product.title.properties
        ?.joinToString(" | ") { "${it.field.name}: ${it.value}" }

    return BasketItemCardModel(
        title = product.title.name,
        properties = propertiesText,
        description = product.title.description,
        price = product.exposedPrice.toInt(),
        amount = amount,
        imageRes = R.drawable.furniture1,
        availableAmount = availableAmount,
        increaseEnabled = increaseEnabled,
        decreaseEnabled = decreaseEnabled,
        unavailable = amount > availableAmount
    )
}

fun BasketItem.toUiModel(): BasketItemUiModel {
    return BasketItemUiModel(
        productId = Id(product.id),
        amount = amount,
        model = this.toModel()
    )
}


fun basketItem(minItem: MinBasketItem, stockItem: StockItemDto): BasketItem {
    return BasketItem(
        product = stockItem.product,
        availableAmount = stockItem.amount,
        amount = minItem.amount,
        increaseEnabled = minItem.amount < stockItem.amount,
        decreaseEnabled = true
    )
}