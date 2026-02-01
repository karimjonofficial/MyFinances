package com.orka.myfinances.fixtures.resources.models.template

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.id3
import com.orka.myfinances.fixtures.resources.types

val template1 = Template(
    id = id1,
    name = "Template 1",
    fields = templateFields,
    description = description
)
val template2 = Template(
    id = id2,
    name = "Template 2",
    fields = templateFields,
    description = description
)
val template3 = Template(
    id = id3,
    name = "Template 3",
    fields = templateFields,
    description = description
)
val template4 = Template(
    id = Id(4),
    name = "Yangi",
    fields = listOf(
        TemplateField(
            id = id1,
            name = "f1",
            type = types[0]
        ),
        TemplateField(
            id = id1,
            name = "f2",
            type = types[0]
        ),
        TemplateField(
            id = id1,
            name = "f3",
            type = types[0]
        ),
        TemplateField(
            id = id1,
            name = "f4",
            type = types[0]
        ),
        TemplateField(
            id = id1,
            name = "f5",
            type = types[0]
        ),
        TemplateField(
            id = id1,
            name = "f6",
            type = types[0]
        ),
    ),
    description = description
)

val templates = listOf(
    template1,
    template2,
    template3,
    template4
)