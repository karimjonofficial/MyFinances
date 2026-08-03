package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.ui.models.item.TemplateScreenModelField

data class TemplateScreenModel(
    val name: String,
    val fields: List<TemplateScreenModelField>?,
    val description: String?
)