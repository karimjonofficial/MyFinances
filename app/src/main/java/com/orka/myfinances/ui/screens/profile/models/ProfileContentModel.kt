package com.orka.myfinances.ui.screens.profile.models

data class ProfileContentModel(
    val officeName: String,
    val offices: List<OfficeItemModel>,
    val name: String,
    val phone: String?
)