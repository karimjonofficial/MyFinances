package com.orka.myfinances.ui.managers.dialog

import com.orka.myfinances.ui.screens.home.HomeScreenViewModel

sealed interface DialogState {
    data class AddFolder(val viewModel: HomeScreenViewModel) : DialogState
}