package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.profile.models.OfficeItemModel

fun OfficeApiModel.toItemModel(): OfficeItemModel {
    return OfficeItemModel(
        id = Id(id),
        title = name
    )
}