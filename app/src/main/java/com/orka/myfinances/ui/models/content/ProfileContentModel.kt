package com.orka.myfinances.ui.models.content

import com.orka.myfinances.ui.models.sheet.BranchItemModel

data class ProfileContentModel(
    val branchName: String,
    val branches: List<BranchItemModel>,
    val name: String,
    val phone: String?
)