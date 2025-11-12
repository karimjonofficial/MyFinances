package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.folder.folder1

val sizeField = TemplateField(id1, "Size", "String")
val colorField = TemplateField(id1, "Color", "String")
val product1 = Product(
    id = id1,
    name = "Classic Canvas Sneakers",
    price = 0,
    salePrice = 0,
    warehouse = folder1,
    properties = listOf(
        Property(Id(304), sizeField, "9"),
        Property(Id(305), colorField, "Red")
    ),
    description = "A timeless sneaker design for any casual occasion."
)