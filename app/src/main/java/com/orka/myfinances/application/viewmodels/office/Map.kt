package com.orka.myfinances.application.viewmodels.office

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel

fun Office.toUiModel(): OfficeUiModel {
    return OfficeUiModel(
        name = name,
        officeId = this.id
    )
}