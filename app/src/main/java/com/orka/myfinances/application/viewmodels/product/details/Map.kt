package com.orka.myfinances.application.viewmodels.product.details

import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.dtos.product.title.PropertyDto
import com.orka.myfinances.ui.models.screen.ProductTitleScreenModel
import com.orka.myfinances.ui.models.item.PropertyModel

fun ProductTitleDto.toScreenModel(): ProductTitleScreenModel {
    return ProductTitleScreenModel(
        title = name,
        dateTime = createdAt,
        price = defaultSalePrice.toInt(),
        properties = properties?.map { it.toModel() },
        description = description,
        salePrice = defaultPrice.toInt()
    )
}

fun PropertyDto.toModel(): PropertyModel {
    return PropertyModel(
        name = field.name,
        value = when (field.type) {
            "text" -> value
            "number" -> value
            "date" -> value
            "boolean" -> if (value.toBoolean()) "Yes" else "No"
            else -> "Unknown type"
        }
    )
}
