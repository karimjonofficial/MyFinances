package com.orka.myfinances.ui.screens.checkout

import com.orka.myfinances.data.api.product.ProductApiModel
import com.orka.myfinances.data.api.template.models.response.TemplateApiModelField
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.api.title.models.response.PropertyApiModel
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel

fun Product.map(): ProductApiModel {
    return ProductApiModel(
        id = id.value,
        title = title.map(),
        price = price.toLong(),
        salePrice = salePrice.toLong(),
        createdAt = dateTime,
        modifiedAt = dateTime
    )
}

fun ProductTitle.map(): ProductTitleApiModel {
    return ProductTitleApiModel(
        id = id.value,
        name = name,
        description = description,
        properties = properties.map { it.map() },
        createdAt = dateTime,
        modifiedAt = dateTime,
        category = category.id.value,
        defaultPrice = defaultPrice.toLong(),
        defaultSalePrice = defaultSalePrice.toLong()
    )
}

fun Property.map(): PropertyApiModel {
    return PropertyApiModel(
        id = id.value,
        field = field.map(),
        value = value.toString()
    )
}

fun TemplateField.map(): TemplateApiModelField {
    return TemplateApiModelField(
        id = id.value,
        name = name,
        type = type
    )
}

fun Client.map(): ClientItemModel {
    return ClientItemModel(
        id = id,
        name = "$firstName $lastName"
    )
}