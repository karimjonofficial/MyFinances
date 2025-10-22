package com.orka.myfinances.fixtures.resources.models.template

import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.id3

val templateField1 = TemplateField(
    id = id1,
    name = "TemplateField 1",
    type = "number"
)
val templateField2 = TemplateField(
    id = id2,
    name = "TemplateField 2",
    type = "text"
)
val templateField3 = TemplateField(
    id = id3,
    name = "TemplateField 3",
    type = "range"
)

val templateFields = listOf(
    templateField1,
    templateField2,
    templateField3
)