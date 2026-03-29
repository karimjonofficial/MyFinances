package com.orka.myfinances.data.api.title

import com.orka.myfinances.data.api.folder.toEntity
import com.orka.myfinances.data.api.title.models.request.AddProductTitleApiRequest
import com.orka.myfinances.data.api.title.models.request.PropertyApiRequestModel
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.api.title.models.response.PropertyApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.fixtures.resources.Types

fun ProductTitleApiModel.toEntity(category: Category): ProductTitle {
    return ProductTitle(
        id = Id(id),
        name = name,
        defaultPrice = defaultPrice.toInt(),
        defaultSalePrice = defaultSalePrice.toInt(),
        dateTime = createdAt,
        category = category,
        properties = properties.map { it.toEntity() }
    )
}

fun PropertyApiModel.toEntity(): Property {
    return Property(
        id = Id(id),
        field = field.toEntity(),
        value = when(field.type) {
            Types.TEXT -> value
            Types.NUMBER -> value.toInt()
            Types.BOOLEAN -> value.toBoolean()
            else -> throw Exception()
        }
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