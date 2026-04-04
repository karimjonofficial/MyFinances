package com.orka.myfinances.data.api.title

import com.orka.myfinances.data.api.title.models.request.AddProductTitleApiRequest
import com.orka.myfinances.data.api.title.models.request.PropertyApiRequestModel
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel

fun AddProductTitleRequest.toApiRequest(office: Office): AddProductTitleApiRequest {
    return AddProductTitleApiRequest(
        name = name,
        category = categoryId.value,
        properties = properties.map { it.toApiRequest() },
        defaultPrice = price.toLong(),
        defaultSalePrice = salePrice.toLong(),
        description = description,
        branch = office.id.value
    )
}

fun PropertyModel<*>.toApiRequest(): PropertyApiRequestModel {
    return PropertyApiRequestModel(
        id = this.fieldId.value,
        value = value.toString()
    )
}