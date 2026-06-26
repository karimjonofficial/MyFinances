package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.api.template.models.response.TemplateApiModelField
import com.orka.myfinances.data.api.title.models.request.AddProductTitleApiRequest
import com.orka.myfinances.data.api.title.models.request.PropertyApiRequestModel
import com.orka.myfinances.data.api.title.models.request.UpdateProductTitleApiRequest
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.api.title.models.response.PropertyApiModel
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.dtos.product.title.PropertyDto
import com.orka.myfinances.data.dtos.template.TemplateFieldDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.data.repositories.product.title.models.UpdateProductTitleRequest

fun ProductTitleApiModel.toDto(): ProductTitleDto {
    return ProductTitleDto(
        id = id,
        category = category,
        name = name,
        properties = properties.map { it.toDto() },
        defaultPrice = defaultPrice,
        defaultSalePrice = defaultSalePrice,
        defaultExposedPrice = defaultExposedPrice,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
        description = description
    )
}

fun PropertyApiModel.toDto(): PropertyDto {
    return PropertyDto(
        id = id,
        field = field.toDto(),
        value = value
    )
}

fun TemplateApiModelField.toDto(): TemplateFieldDto {
    return TemplateFieldDto(
        id = id,
        name = name,
        type = type
    )
}

fun AddProductTitleRequest.toApiRequest(officeId: Id): AddProductTitleApiRequest {
    return AddProductTitleApiRequest(
        name = name,
        category = categoryId.value,
        properties = properties.map { it.toApiRequest() },
        defaultPrice = price.toLong(),
        defaultSalePrice = salePrice.toLong(),
        defaultExposedPrice = exposedPrice.toLong(),
        description = description,
        branch = officeId.value
    )
}

fun PropertyModel<*>.toApiRequest(): PropertyApiRequestModel {
    return PropertyApiRequestModel(
        id = this.fieldId.value,
        value = value.toString()
    )
}

fun UpdateProductTitleRequest.toApiRequest(officeId: Id): UpdateProductTitleApiRequest {
    return UpdateProductTitleApiRequest(
        name = name,
        category = categoryId.value,
        properties = properties.map { it.toApiRequest() },
        defaultPrice = price.toLong(),
        defaultSalePrice = salePrice.toLong(),
        defaultExposedPrice = exposedPrice.toLong(),
        description = description,
        branch = officeId.value
    )
}

