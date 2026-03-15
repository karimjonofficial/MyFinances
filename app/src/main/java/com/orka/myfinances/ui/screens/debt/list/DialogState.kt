package com.orka.myfinances.ui.screens.debt.list

sealed interface DialogState {
    object Loading : DialogState
    data class Success(val clients: List<ClientItemModel>) : DialogState
}
