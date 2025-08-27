package com.orka.myfinances.fixtures.resources.models.template

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.id3

val template1 = Template(
    id = id1,
    name = "Template 1",
    fields = templateFields
)
val template2 = Template(
    id = id2,
    name = "Template 2",
    fields = templateFields
)
val template3 = Template(
    id = id3,
    name = "Template 3",
    fields = templateFields
)

val templates = listOf(
    template1,
    template2,
    template3
)