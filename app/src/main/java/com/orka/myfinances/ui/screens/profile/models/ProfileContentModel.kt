package com.orka.myfinances.ui.screens.profile.models

data class ProfileContentModel(
    val branchName: String,
    val branches: List<BranchItemModel>,
    val name: String,
    val phone: String?
)