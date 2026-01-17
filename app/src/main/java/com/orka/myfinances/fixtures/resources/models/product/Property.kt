package com.orka.myfinances.fixtures.resources.models.product

import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2

val sizeField = TemplateField(id1, "Size", "String")
val colorField = TemplateField(id1, "Color", "String")

val property1 = Property(id1, sizeField, "9")
val property2 = Property(id2, colorField, "Red")

val properties = listOf(property1, property2)