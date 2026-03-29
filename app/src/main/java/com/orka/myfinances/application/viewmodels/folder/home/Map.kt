package com.orka.myfinances.application.viewmodels.folder.home

import com.orka.myfinances.data.api.template.TemplateApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.folder.models.TemplateItemModel

fun TemplateApiModel.toItemModel(): TemplateItemModel {
    return TemplateItemModel(
        id = Id(id),
        name = name
    )
}