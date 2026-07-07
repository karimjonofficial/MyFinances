package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.product.ProductDto
import com.orka.myfinances.testFixtures.resources.dateTime

val productDto1 = ProductDto(
    id = 1,
    title = productTitleDto1,
    price = 1000,
    salePrice = 1100,
    exposedPrice = 1200,
    createdAt = dateTime,
    modifiedAt = dateTime
)

val productDtos = listOf(productDto1)
