package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.application.viewmodels.product.add.toEntity
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle

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