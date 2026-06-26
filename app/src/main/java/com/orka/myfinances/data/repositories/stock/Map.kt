package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.api.product.ProductApiModel
import com.orka.myfinances.data.api.stock.models.StockItemApiModel
import com.orka.myfinances.data.dtos.product.ProductDto
import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.repositories.product.title.toDto

fun StockItemApiModel.toDto(): StockItemDto {
    return StockItemDto(
        id = id,
        product = product.toDto(),
        amount = amount,
        dateTime = dateTime,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}

fun ProductApiModel.toDto(): ProductDto {
    return ProductDto(
        id = id,
        title = title.toDto(),
        price = price,
        salePrice = salePrice,
        exposedPrice = exposedPrice,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}
