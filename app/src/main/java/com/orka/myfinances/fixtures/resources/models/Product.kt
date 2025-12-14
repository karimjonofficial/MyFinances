package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.folder.warehouse1
import com.orka.myfinances.fixtures.resources.price
import com.orka.myfinances.fixtures.resources.salePrice
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val product1 = Product(
    id = id1,
    title = productTitle1,
    price = price,
    salePrice = salePrice,
    warehouse = warehouse1,
    dateTime = dateTime,
    properties = properties,
)
@OptIn(ExperimentalTime::class)
val product2 = Product(
    id = id2,
    title = productTitle2,
    price = price,
    salePrice = salePrice,
    warehouse = warehouse1,
    dateTime = dateTime,
    properties = properties
)

val products = listOf(product1, product2)