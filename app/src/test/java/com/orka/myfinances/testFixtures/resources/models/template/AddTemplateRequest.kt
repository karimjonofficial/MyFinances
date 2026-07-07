package com.orka.myfinances.testFixtures.resources.models.template

import com.orka.myfinances.data.repositories.template.requests.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.requests.TemplateFieldModel

val addTemplateRequest1 = AddTemplateRequest(
    name = "Template 1",
    fields = listOf(TemplateFieldModel("Field 1", "text"))
)
