package com.orka.myfinances.data.models.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.TemplateField

data class Property(
    val id: Id,
    val name: String,
    val type: TemplateField,
    val value: Any
)