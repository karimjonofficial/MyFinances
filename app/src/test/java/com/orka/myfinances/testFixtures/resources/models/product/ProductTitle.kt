package com.orka.myfinances.testFixtures.resources.models.product

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.testFixtures.resources.dateTime
import com.orka.myfinances.testFixtures.resources.description
import com.orka.myfinances.testFixtures.resources.models.folder.category1
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.id2
import com.orka.myfinances.testFixtures.resources.name
import com.orka.myfinances.testFixtures.resources.price
import com.orka.myfinances.testFixtures.resources.salePrice

val productTitle1 = ProductTitle(
    id = id1,
    name = name,
    defaultPrice = price,
    defaultSalePrice = salePrice,
    dateTime = dateTime,
    category = category1,
    properties = properties,
    description = description
)
val productTitle2 = ProductTitle(
    id = id2,
    name = name,
    defaultPrice = price,
    defaultSalePrice = salePrice,
    dateTime = dateTime,
    category = category1,
    properties = properties,
    description = description
)

val productTitles = listOf(productTitle1, productTitle2)