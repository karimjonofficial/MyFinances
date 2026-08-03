package com.orka.myfinances.ui.models.sheet

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

data class BranchItemModel(
    override val id: Id,
    override val title: String
) : BottomSheetItemModel