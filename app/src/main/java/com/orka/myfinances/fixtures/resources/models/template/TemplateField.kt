package com.orka.myfinances.fixtures.resources.models.template

import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.id3
import com.orka.myfinances.fixtures.resources.models.id4
import com.orka.myfinances.fixtures.resources.models.id5
import com.orka.myfinances.fixtures.resources.models.id6
import com.orka.myfinances.fixtures.resources.models.id7
import com.orka.myfinances.fixtures.resources.models.id8
import com.orka.myfinances.fixtures.resources.models.id9
import com.orka.myfinances.fixtures.resources.types

val templateField1 = TemplateField(
    id = id1,
    name = "TemplateField 1",
    type = types[1]
)
val templateField2 = TemplateField(
    id = id2,
    name = "TemplateField 2",
    type = types[0]
)
val templateField3 = TemplateField(
    id = id3,
    name = "TemplateField 3",
    type = types[2]
)

val templateField4 = TemplateField(
    id = id4,
    name = "f1",
    type = types[0]
)
val templateField5 = TemplateField(
    id = id5,
    name = "f2",
    type = types[0]
)
val templateField6 = TemplateField(
    id = id6,
    name = "f3",
    type = types[0]
)
val templateField7 = TemplateField(
    id = id7,
    name = "f4",
    type = types[0]
)
val templateField8 = TemplateField(
    id = id8,
    name = "f5",
    type = types[0]
)
val templateField9 = TemplateField(
    id = id9,
    name = "f6",
    type = types[0]
)

val template1Fields = listOf(
    templateField1,
    templateField2,
    templateField3
)
val template4Fields = listOf(
    templateField4,
    templateField5,
    templateField6,
    templateField7,
    templateField8,
    templateField9,
)
val templateFields = listOf(
    templateField1,
    templateField2,
    templateField3,
    templateField4,
    templateField5,
    templateField6,
    templateField7,
    templateField8,
    templateField9,
)