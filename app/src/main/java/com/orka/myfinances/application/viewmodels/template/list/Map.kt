package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.templates.list.TemplateCardModel
import com.orka.myfinances.ui.screens.templates.list.TemplateUiModel

fun TemplateApiModel.toModel(): TemplateCardModel {
    return TemplateCardModel(
        title = name,
        size = "${fields.size}"
    )
}

fun TemplateApiModel.toUiModel(): TemplateUiModel {
    return TemplateUiModel(
        model = toModel(),
        id = Id(id)
    )
}