package com.orka.myfinances.ui.managers.dialog

import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel

//So all viewmodels are provided via states
sealed interface DialogState {
    data class AddFolder(
        val viewModel: HomeScreenViewModel,
        val templatesViewModel: TemplatesScreenViewModel
    ) : DialogState
}