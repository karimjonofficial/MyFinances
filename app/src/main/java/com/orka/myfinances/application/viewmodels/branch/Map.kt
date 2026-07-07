package com.orka.myfinances.application.viewmodels.branch

import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.host.components.BranchUiModel

fun BranchDto.toUiModel(): BranchUiModel {
    return BranchUiModel(
        name = name,
        branchId = Id(id)
    )
}
