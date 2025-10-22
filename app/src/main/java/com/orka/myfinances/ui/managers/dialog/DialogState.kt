package com.orka.myfinances.ui.managers.dialog

//So all viewmodels are provided via states
sealed interface DialogState {
    data class AddFolder(
        val viewModel: Any,
        val templatesViewModel: Any
    ) : DialogState
}