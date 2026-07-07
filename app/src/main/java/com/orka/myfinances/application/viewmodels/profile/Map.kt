package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.profile.models.BranchItemModel

fun BranchDto.toItemModel(): BranchItemModel {
    return BranchItemModel(
        id = Id(id),
        title = name
    )
}
