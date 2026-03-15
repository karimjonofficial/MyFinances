package com.orka.myfinances.ui.screens.templates.details

data class TemplateScreenModel(
    val name: String,
    val fields: List<TemplateScreenModelField>,
    val description: String?
)