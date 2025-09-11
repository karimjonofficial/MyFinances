package com.orka.myfinances.data.models.template

import com.orka.myfinances.data.models.Id

data class TemplateField(
    val id: Id,
    val name: String,
    val type: String
)