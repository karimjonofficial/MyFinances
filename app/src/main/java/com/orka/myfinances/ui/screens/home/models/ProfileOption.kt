package com.orka.myfinances.ui.screens.home.models

data class ProfileOption(
    val index: Int,
    val name: String,
    val action: () -> Unit
)