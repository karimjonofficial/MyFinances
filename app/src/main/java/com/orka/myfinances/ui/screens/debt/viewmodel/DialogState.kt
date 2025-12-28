package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Client

sealed interface DialogState {
    object Loading : DialogState
    data class Success(val clients: List<Client>) : DialogState
}
