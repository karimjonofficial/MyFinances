package com.orka.myfinances.data.repositories.product.models

import com.orka.myfinances.data.models.template.TemplateField

data class PropertyModel<T>(
    val field: TemplateField,
    val value: T
)