package com.orka.myfinances.application.viewmodels.basket

import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.basket.BasketItemUiModel
import com.orka.myfinances.ui.screens.basket.components.BasketItemCardModel

fun BasketItem.toModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): BasketItemCardModel {
    val propertiesText = product.title.properties
        .joinToString(" | ") { "${it.field.name}: ${it.value}" }

    return BasketItemCardModel(
        title = product.title.name,
        properties = propertiesText,
        description = if (product.title.description != null) UiText.Str(product.title.description) else UiText.Res(R.string.no_description_provided),
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
        productTitleId = Id(product.id),
        amount = amount,
        model = this.toModel(formatPrice, formatDecimal)
    )
}
