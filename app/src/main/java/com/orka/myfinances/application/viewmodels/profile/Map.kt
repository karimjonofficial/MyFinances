package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.data.dtos.office.OfficeDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.profile.models.OfficeItemModel

fun OfficeDto.toItemModel(): OfficeItemModel {
    return OfficeItemModel(
        id = Id(id),
        title = name
    )
}
