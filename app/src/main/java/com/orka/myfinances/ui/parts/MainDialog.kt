package com.orka.myfinances.ui.parts

import androidx.compose.runtime.Composable
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.managers.navigation.NavigationManager

@Composable
fun MainDialog(
    dialogState: DialogState,
    dialogManager: DialogManager,
    navigationManager: NavigationManager
) {
    when(dialogState) {
        is DialogState.AddFolder -> {
            AddFolderDialog(
                templates = emptyList(),
                dismissRequest = { dialogManager.hide() },
                onAddTemplateClick = { navigationManager.navigateToAddTemplate() },
                onSuccess = { name, type ->
                    dialogState.viewModel.addFolder(name, type)
                    dialogManager.hide()
                },
                onCancel = { dialogManager.hide() }
            )
        }
    }
}