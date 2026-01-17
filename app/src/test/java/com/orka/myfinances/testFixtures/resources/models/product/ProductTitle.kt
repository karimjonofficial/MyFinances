package com.orka.myfinances.testFixtures.resources.models.product

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.testFixtures.resources.dateTime
import com.orka.myfinances.testFixtures.resources.models.folder.category1
import com.orka.myfinances.testFixtures.resources.name
import com.orka.myfinances.testFixtures.resources.price
import com.orka.myfinances.testFixtures.resources.salePrice

val productTitle1 = ProductTitle(
    id = com.orka.myfinances.fixtures.resources.models.id1,
    name = name,
    defaultPrice = price,
    defaultSalePrice = salePrice,
    dateTime = dateTime,
    category = category1,
    properties = properties,
    description = com.orka.myfinances.fixtures.resources.description
)
val productTitle2 = ProductTitle(
    id = com.orka.myfinances.fixtures.resources.models.id2,
    name = name,
    defaultPrice = price,
    defaultSalePrice = salePrice,
    dateTime = dateTime,
    category = category1,
    properties = properties,
    description = com.orka.myfinances.fixtures.resources.description
)

val productTitles = listOf(productTitle1, productTitle2)