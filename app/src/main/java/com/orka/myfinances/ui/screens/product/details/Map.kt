package com.orka.myfinances.ui.screens.product.details

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.ui.models.screen.ProductTitleScreenModel
import com.orka.myfinances.ui.models.item.PropertyModel
import kotlin.time.Instant

fun ProductTitle.toModel(): ProductTitleScreenModel {
    return ProductTitleScreenModel(
        title = name,
        dateTime = dateTime,
        price = defaultSalePrice,
        properties = properties.map { it.toModel() },
        description = description,
        salePrice = defaultSalePrice
    )
}

fun Property.toModel(): PropertyModel {
    return PropertyModel(
        name = field.name,
        value = when (field.type) {
            "text" -> value as String
            "number" -> (value as Int).toString()
            "date" -> (value as Instant).toString()
            "boolean" -> if(value as Boolean) "Yes" else "No"
            else -> "Unknown type"
        }
    )
}
