package com.orka.myfinances.application.viewmodels.product.details

import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.api.title.models.response.PropertyApiModel
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.product.details.models.ProductTitleScreenModel
import com.orka.myfinances.ui.screens.product.details.models.PropertyModel
import kotlin.time.Instant

fun ProductTitleApiModel.toModel(
    formatDecimal: FormatDecimal,
    formatDate: FormatDate,
    formatPrice: FormatPrice
): ProductTitleScreenModel {
    return ProductTitleScreenModel(
        title = name,
        dateTime = formatDate.formatDate(createdAt),
        price = formatPrice.formatPrice(defaultSalePrice.toDouble()),
        properties = properties.map { it.toModel(formatDecimal, formatDate) },
        description = description,
        salePrice = defaultPrice.toInt()
    )
}

fun PropertyApiModel.toModel(
    formatDecimal: FormatDecimal,
    formatDate: FormatDate
): PropertyModel {
    return PropertyModel(
        name = field.name,
        value = when (field.type) {
            "text" -> value
            "number" -> formatDecimal.formatDecimal((value.toDouble()))
            "date" -> formatDate.formatDate(Instant.parse(value))
            "boolean" -> if (value.toBoolean()) "Yes" else "No"
            else -> "Unknown type"
        }
    )
}