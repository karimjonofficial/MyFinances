package com.orka.myfinances.ui.screens.add.product

import com.orka.myfinances.data.models.template.TemplateField

data class PropertyModel<T>(
    val field: TemplateField,
    val value: T
)