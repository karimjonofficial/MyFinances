package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.data.models.Branch
import com.orka.myfinances.ui.screens.profile.models.BranchItemModel

fun Branch.toItemModel(): BranchItemModel {
    return BranchItemModel(
        id = id,
        title = name
    )
}