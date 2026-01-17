package com.orka.myfinances.fixtures.resources.models.product

import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.price
import com.orka.myfinances.fixtures.resources.productName1
import com.orka.myfinances.fixtures.resources.productName2
import com.orka.myfinances.fixtures.resources.salePrice

val productTitle1 = ProductTitle(
    id = id1,
    name = productName1,
    defaultPrice = price,
    defaultSalePrice = salePrice,
    dateTime = dateTime,
    category = category1,
    properties = properties,
    description = description
)
val productTitle2 = ProductTitle(
    id = id2,
    name = productName2,
    defaultPrice = price,
    defaultSalePrice = salePrice,
    dateTime = dateTime,
    category = category1,
    properties = properties,
    description = description
)

val productTitles = listOf(productTitle1, productTitle2)