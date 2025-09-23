package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.fixtures.resources.models.folder.folder1

val product1 = Product(
    id = id1,
    name = "Product 1",
    price = 100.0,
    salePrice = 110.0,
    warehouse = folder1,
    properties = emptyList()
)