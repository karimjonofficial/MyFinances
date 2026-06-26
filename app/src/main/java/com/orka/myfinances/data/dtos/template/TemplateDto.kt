package com.orka.myfinances.data.dtos.template

data class TemplateDto(
    val id: Int,
    val name: String,
    val fields: List<TemplateFieldDto>,
    val description: String?,
)
