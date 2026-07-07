package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.template.TemplateDto

val templateDto1 = TemplateDto(
    id = 1,
    name = "Template 1",
    fields = listOf(templateFieldDto1),
    description = "Description 1"
)

val templateDtos = listOf(templateDto1)
