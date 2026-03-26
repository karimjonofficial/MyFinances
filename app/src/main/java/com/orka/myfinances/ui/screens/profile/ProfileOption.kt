package com.orka.myfinances.ui.screens.profile

data class ProfileOption(
    val index: Int,
    val titleRes: Int,
    val action: () -> Unit
)