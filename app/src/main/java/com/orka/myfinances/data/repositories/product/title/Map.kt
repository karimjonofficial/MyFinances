package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.api.title.AddProductTitleApiRequest
import com.orka.myfinances.data.api.title.ProductTitleApiModel
import com.orka.myfinances.data.api.title.PropertyApiModel
import com.orka.myfinances.data.api.title.PropertyApiRequestModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.data.repositories.template.TemplateFieldApiModel
import com.orka.myfinances.fixtures.resources.Types

fun ProductTitleApiModel.map(category: Category): ProductTitle {
    return ProductTitle(
        id = Id(id),
        name = name,
        defaultPrice = defaultPrice.toInt(),
        defaultSalePrice = defaultSalePrice.toInt(),
        dateTime = createdAt,
        category = category,
        properties = properties.map { it.map() }
    )
}

fun PropertyApiModel.map(): Property {
    return Property(
        id = Id(id),
        field = field.map(),
        value = when(field.type) {
            Types.TEXT -> value
            Types.NUMBER -> value.toInt()
            Types.BOOLEAN -> value.toBoolean()
            else -> throw Exception()
        }
    )
}

fun TemplateFieldApiModel.map(): TemplateField {
    return TemplateField(
        id = Id(id),
        name = name,
        type = type
    )
}

fun AddProductTitleRequest.map(): AddProductTitleApiRequest {
    return AddProductTitleApiRequest(
        name = name,
        category = category.id.value,
        properties = properties.map { it.map() },
        defaultPrice = price.toLong(),
        defaultSalePrice = salePrice.toLong(),
        description = description
    )
}

fun PropertyModel<*>.map(): PropertyApiRequestModel {
    return PropertyApiRequestModel(
        id = this.fieldId.value,
        value = value.toString()
    )
}