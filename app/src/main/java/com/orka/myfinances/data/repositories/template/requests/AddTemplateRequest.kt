package com.orka.myfinances.data.repositories.template.requests

data class AddTemplateRequest(
    val name: String,
    val fields: List<TemplateFieldModel>
)