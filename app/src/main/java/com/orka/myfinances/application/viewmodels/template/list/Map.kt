package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.screens.templates.list.TemplateCardModel

fun Template.toModel(): TemplateCardModel {
    return TemplateCardModel(
        title = name,
        size = "${fields.size}"
    )
}

fun Template.toUiModel(): com.orka.myfinances.ui.screens.templates.list.TemplateUiModel {
    return _root_ide_package_.com.orka.myfinances.ui.screens.templates.list.TemplateUiModel(
        model = toModel(),
        template = this
    )
}