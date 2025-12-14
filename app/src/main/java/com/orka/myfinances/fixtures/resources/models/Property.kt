package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.TemplateField


val sizeField = TemplateField(id1, "Size", "String")
val colorField = TemplateField(id1, "Color", "String")

val property1 = Property(id1, sizeField, "9")
val property2 = Property(id2, colorField, "Red")

val properties = listOf(property1, property2)