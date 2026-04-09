package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.ui.screens.profile.models.OfficeItemModel

fun Office.toItemModel(): OfficeItemModel {
    return OfficeItemModel(
        id = id,
        title = name
    )
}