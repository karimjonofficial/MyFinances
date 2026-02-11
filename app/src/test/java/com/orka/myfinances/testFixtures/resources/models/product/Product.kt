package com.orka.myfinances.testFixtures.resources.models.product

import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.testFixtures.resources.dateTime
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.id2
import com.orka.myfinances.testFixtures.resources.price
import com.orka.myfinances.testFixtures.resources.salePrice

val product1 = Product(
    id = id1,
    title = productTitle1,
    dateTime = dateTime,
    price = price,
    salePrice = salePrice
)

val product2 = Product(
    id = id2,
    title = productTitle2,
    dateTime = dateTime,
    price = price,
    salePrice = salePrice
)