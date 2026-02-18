package com.orka.myfinances.ui.screens.product.viewmodel

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.product.ProductTitleModel
import com.orka.myfinances.ui.screens.product.PropertyModel
import kotlin.time.Instant

fun ProductTitle.toModel(formatDecimal: FormatDecimal, formatDate: FormatDate, formatPrice: FormatPrice): ProductTitleModel {
    return ProductTitleModel(
        title = name,
        dateTime = formatDate.formatDate(dateTime),
        price = formatPrice.formatPrice(defaultSalePrice.toDouble()),
        properties = properties.map { it.toModel(formatDecimal, formatDate) },
        description = description
    )
}

fun Property.toModel(formatDecimal: FormatDecimal, formatDate: FormatDate): PropertyModel {
    return PropertyModel(
        name = type.name,
        value = when(type.type) {
            "text" -> value as String
            "number" -> formatDecimal.formatDecimal((value as Int).toDouble())
            "date" -> formatDate.formatDate((value as Instant))
            "boolean" -> if(value as Boolean) "Yes" else "No"
            else -> "Unknown type"
        }
    )
}