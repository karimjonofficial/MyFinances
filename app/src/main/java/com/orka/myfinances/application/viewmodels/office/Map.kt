package com.orka.myfinances.application.viewmodels.office

import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel

fun OfficeApiModel.toUiModel(): OfficeUiModel {
    return OfficeUiModel(
        name = name,
        officeId = Id(id)
    )
}