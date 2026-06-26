package com.orka.myfinances.application.viewmodels.folder.home

import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.TemplateItemModel

fun TemplateDto.toItemModel(): TemplateItemModel {
    return TemplateItemModel(
        id = Id(id),
        title = name
    )
}