package com.orka.myfinances.application.viewmodels.basket

import com.orka.myfinances.R
import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.MinBasketItem
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.ui.screens.basket.BasketItemUiModel
import com.orka.myfinances.ui.screens.basket.components.BasketItemCardModel

fun BasketItem.toModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): BasketItemCardModel {
    val propertiesText = product.title.properties
        ?.joinToString(" | ") { "${it.field.name}: ${it.value}" }

    return BasketItemCardModel(
        title = product.title.name,
        properties = propertiesText,
        description = description,
        price = formatPrice.formatPrice(product.exposedPrice.toDouble()),
        amount = formatDecimal.formatDecimal(amount.toDouble()),
        imageRes = R.drawable.furniture1,
        availableAmount = formatDecimal.formatDecimal(availableAmount.toDouble()),
        increaseEnabled = increaseEnabled,
        decreaseEnabled = decreaseEnabled,
        unavailable = amount > availableAmount
    )
}

fun BasketItem.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): BasketItemUiModel {
    return BasketItemUiModel(
        productId = Id(product.id),
        amount = amount,
        model = this.toModel(formatPrice, formatDecimal)
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