package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.dtos.product.title.PropertyDto
import com.orka.myfinances.data.dtos.template.TemplateFieldDto
import com.orka.myfinances.testFixtures.resources.dateTime

val templateFieldDto1 = TemplateFieldDto(
    id = 1,
    name = "Color",
    type = "text"
)

val propertyDto1 = PropertyDto(
    id = 1,
    field = templateFieldDto1,
    value = "Red"
)

val productTitleDto1 = ProductTitleDto(
    id = 1,
    category = 1,
    name = "Product 1",
    properties = listOf(propertyDto1),
    defaultPrice = 1000,
    defaultSalePrice = 1100,
    defaultExposedPrice = 1200,
    createdAt = dateTime,
    modifiedAt = dateTime,
    description = "Description 1"
)

val productTitleDtos = listOf(productTitleDto1)
