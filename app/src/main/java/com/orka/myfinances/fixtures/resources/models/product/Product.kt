package com.orka.myfinances.fixtures.resources.models.product

import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.price
import com.orka.myfinances.fixtures.resources.salePrice

val product1 = Product(
    id = id1,
    title = productTitle1,
    price = price,
    salePrice = salePrice,
    dateTime = dateTime,
    description = description
)
val product2 = Product(
    id = id2,
    title = productTitle2,
    price = price,
    salePrice = salePrice,
    dateTime = dateTime,
    description = description
)

val products = listOf(product1, product2)