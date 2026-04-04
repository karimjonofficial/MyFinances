package com.orka.myfinances.ui.screens.folder.models

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

data class TemplateItemModel(
    val id: Id,
    override val title: String
) : BottomSheetItemModel