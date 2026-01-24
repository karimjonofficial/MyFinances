package com.orka.myfinances.data.repositories.template

data class AddTemplateRequest(
    val name: String,
    val fields: List<TemplateFieldModel>
)