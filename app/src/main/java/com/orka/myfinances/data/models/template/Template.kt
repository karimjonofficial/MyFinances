package com.orka.myfinances.data.models.template

import com.orka.myfinances.data.models.Id

data class Template(
    val id: Id,
    val name: String,
    val fields: List<TemplateField>
)