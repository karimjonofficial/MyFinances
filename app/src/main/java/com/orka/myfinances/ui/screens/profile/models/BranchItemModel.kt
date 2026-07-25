package com.orka.myfinances.ui.screens.profile.models

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

data class BranchItemModel(
    override val id: Id,
    override val title: String
) : BottomSheetItemModel
