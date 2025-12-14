package com.orka.myfinances.testFixtures.resources.models.product

import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.testFixtures.resources.dateTime
import com.orka.myfinances.testFixtures.resources.description
import com.orka.myfinances.testFixtures.resources.models.folder.warehouse1
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.id2
import com.orka.myfinances.testFixtures.resources.price
import com.orka.myfinances.testFixtures.resources.salePrice
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val product1 = Product(
    id = id1,
    title = productTitle1,
    price = price,
    salePrice = salePrice,
    warehouse = warehouse1,
    properties = emptyList(),
    dateTime = dateTime,
    description = description
)

@OptIn(ExperimentalTime::class)
val product2 = Product(
    id = id2,
    title = productTitle2,
    price = price,
    salePrice = salePrice,
    warehouse = warehouse1,
    properties = emptyList(),
    dateTime = dateTime,
    description = description
)

val products = listOf(product1, product2)