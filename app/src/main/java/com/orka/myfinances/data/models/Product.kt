package com.orka.myfinances.data.models

import com.orka.myfinances.data.models.template.Template

data class Product(
    val id: Id,
    val template: Template
)