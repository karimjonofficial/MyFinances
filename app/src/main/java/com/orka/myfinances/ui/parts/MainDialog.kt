package com.orka.myfinances.ui.parts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.templates.TemplatesScreenState
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel

@Composable
fun MainDialog(
    dialogState: DialogState,
    dialogManager: DialogManager,
    navigationManager: NavigationManager
) {
    when (dialogState) {
        is DialogState.AddFolder -> {
            val uiState = (dialogState.templatesViewModel as TemplatesScreenViewModel).uiState.collectAsState()
            val state = uiState.value

            if (state is TemplatesScreenState.Success) {
                AddFolderDialog(
                    templates = state.templates,
                    dismissRequest = { dialogManager.hide() },
                    onAddTemplateClick = {
                        navigationManager.navigateToAddTemplate()
                        dialogManager.hide()
                    },
                    onSuccess = { name, type ->
                        (dialogState.viewModel as HomeScreenViewModel).addFolder(name, type)
                        dialogManager.hide()
                    },
                    onCancel = { dialogManager.hide() }
                )
            }
        }
    }
}