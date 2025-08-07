package com.orka.myfinances.data.models

data class ProductTemplate(
    val id: Id,
    val name: String,
    val fields: List<TemplateField>
)