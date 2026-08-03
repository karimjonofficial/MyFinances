package com.orka.myfinances.ui.models.sheet

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

data class CategoryBottomSheetItemModel(
    override val id: Id,
    override val title: String,
    val template: Template
) : BottomSheetItemModel