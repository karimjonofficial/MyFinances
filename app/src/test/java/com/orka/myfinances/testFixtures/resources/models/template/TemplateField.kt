package com.orka.myfinances.testFixtures.resources.models.template

import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.testFixtures.resources.int
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.name
import com.orka.myfinances.testFixtures.resources.text

val textTemplateField = TemplateField(
    id = id1,
    name = name,
    type = text
)
val intTemplateField = TemplateField(
    id = id1,
    name = name,
    type = int
)
val templateFields = listOf(
    textTemplateField,
    intTemplateField
)