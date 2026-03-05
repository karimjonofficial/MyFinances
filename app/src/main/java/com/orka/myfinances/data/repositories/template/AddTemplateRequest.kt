package com.orka.myfinances.data.repositories.template

import com.orka.myfinances.ui.screens.templates.add.TemplateFieldModel

data class AddTemplateRequest(
    val name: String,
    val fields: List<TemplateFieldModel>
)