package com.orka.myfinances.ui.screens.home.viewmodel.basket

import com.orka.myfinances.R
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.home.components.BasketItemCardModel

fun BasketItem.toModel(
    priceFormatter: FormatPrice,
    decimalFormatter: FormatDecimal
): BasketItemCardModel {
    val product = product
    val propertiesText = product.title.properties
        .joinToString(" | ") { "${it.type.name}: ${it.value}" }

    return BasketItemCardModel(
        title = product.title.name,
        properties = propertiesText,
        description = if (product.title.description != null) UiText.Str(product.title.description) else UiText.Res(R.string.no_description_provided),
        price = priceFormatter.formatPrice(product.salePrice.toDouble()),
        amount = decimalFormatter.formatDecimal(amount.toDouble()),
        imageRes = R.drawable.furniture1 // Default image or map from category if possible
    )
}
