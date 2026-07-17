package com.orka.myfinances.ui.screens.folder.home

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.models.item.TemplateItemModel

fun Template.toItemModel(): TemplateItemModel {
    return TemplateItemModel(
        id = id,
        title = name
    )
}