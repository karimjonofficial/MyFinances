package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Product
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.fixtures.resources.models.template.template2

val product1 = Product(
    id = id1,
    name = "Product 1",
    price = 100.0,
    salePrice = 110.0,
    template = template1
)
val product2 = Product(
    id = id2,
    name = "Product 2",
    price = 200.0,
    salePrice = 220.0,
    template = template2
)