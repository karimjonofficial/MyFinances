package com.orka.myfinances.ui.screens.folder.home

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.navigation.entries.home.TemplateItemModel

fun Template.toItemModel(): TemplateItemModel {
    return TemplateItemModel(
        id = id,
        name = name
    )
}