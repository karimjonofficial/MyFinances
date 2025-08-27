package com.orka.myfinances.ui.navigation

import kotlinx.serialization.Serializable

sealed interface DialogState {
    @Serializable
    data object AddProduct : DialogState
}