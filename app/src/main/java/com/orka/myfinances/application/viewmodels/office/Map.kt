package com.orka.myfinances.application.viewmodels.office

import com.orka.myfinances.data.dtos.office.OfficeDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel

fun OfficeDto.toUiModel(): OfficeUiModel {
    return OfficeUiModel(
        name = name,
        officeId = Id(id)
    )
}
