package com.orka.myfinances.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.viewmodel.viewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    session: Session,
    factory: Factory,
    sessionManager: SessionManager
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    fun showDialog() {
        dialogVisible.value = true
    }
    fun hideDialog() {
        dialogVisible.value = false
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            val foldersViewModel = viewModel(session.office) {
                factory.foldersViewModel()
            }
            HomeScreenTopBar(
                onAddClick = { showDialog() },
                onNotificationsClick = { foldersViewModel.navigateToNotifications() },
                onSearchClick = { foldersViewModel.navigateToSearch() }
            )
        },
        bottomBar = {}
    ) { paddingValues ->
        val m = Modifier.scaffoldPadding(paddingValues)

        val foldersViewModel = viewModel(session.office) {
            factory.foldersViewModel()
        }
        LaunchedEffect(session) {
            foldersViewModel.initialize()
        }
        val state = foldersViewModel.uiState.collectAsState()

        FoldersContent(
            modifier = m,
            state = state.value,
            interactor = foldersViewModel
        )

        if (dialogVisible.value) {
            val dialogState = foldersViewModel.dialogState.collectAsState()

            AddFolderDialog(
                state = dialogState.value,
                dismissRequest = { hideDialog() },
                onAddTemplateClick = {
                    foldersViewModel.navigateToAddTemplate()
                    hideDialog()
                },
                onSuccess = { name, type, templateId ->
                    foldersViewModel.addFolder(name, type, templateId)
                    hideDialog()
                },
                onCancel = { hideDialog() }
            )
        }
    }
}
