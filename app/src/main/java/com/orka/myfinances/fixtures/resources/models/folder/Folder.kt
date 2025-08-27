package com.orka.myfinances.fixtures.resources.models.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Product
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.id3
import com.orka.myfinances.fixtures.resources.models.id4
import com.orka.myfinances.fixtures.resources.models.product1
import com.orka.myfinances.fixtures.resources.models.product2
import com.orka.myfinances.fixtures.resources.models.template.template1
import com.orka.myfinances.fixtures.resources.models.template.template2
import com.orka.myfinances.fixtures.resources.models.template.template3
import kotlin.collections.get


val folder1 = ProductFolder(
    id = id1,
    name = "Smartphones",
    template = template1,
    products = listOf(product1),
    stockItems = emptyList()//TODO generate
)
val folder2 = ProductFolder(
    id = id2,
    name = "Laptops",
    template = template2,
    products = listOf(product2),
    stockItems = emptyList()//TODO generate
)
val folder3 = Catalog(
    id = id3,
    name = "Home Appliances",
    folders = listOf(
        ProductFolder(
            id = Id(201),
            name = "Refrigerators",
            template = templates[2],
            products = listOf(
                Product(Id(9101), "Product5", 500.0, 450.0, templates[2]),
                Product(Id(9102), "Product6", 600.0, 540.0, templates[2])
            ),
            stockItems = emptyList()//TODO generate
        )
    )
)
val folder4 = ProductFolder(
    id = id4,
    name = "Standalone Gadgets",
    template = template3,
    products = listOf(
        Product(Id(9201), "Product7", 700.0, 630.0, templates[3]),
        Product(Id(9202), "Product8", 800.0, 720.0, templates[3])
    ),
    stockItems = emptyList()//TODO generate
)
val folder5 = Catalog(
    id = id3,
    name = "Electronics",
    folders = listOf(folder3, folder4)
)

val folders = listOf(folder1, folder2, folder5)