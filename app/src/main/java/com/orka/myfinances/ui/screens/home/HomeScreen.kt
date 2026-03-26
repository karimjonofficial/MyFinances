package com.orka.myfinances.ui.screens.home

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.viewmodel.viewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.basket.BasketContent
import com.orka.myfinances.ui.screens.basket.BasketScreenTopBar
import com.orka.myfinances.ui.screens.folder.home.FoldersContent
import com.orka.myfinances.ui.screens.folder.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentTopBar
import com.orka.myfinances.ui.screens.profile.ProfileContent
import com.orka.myfinances.ui.screens.profile.ProfileTopBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    session: Session,
    factory: Factory,
    sessionManager: SessionManager
) {
    val navItems = navItems()
    val navState = rememberSaveable { mutableIntStateOf(0) }
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    fun NavItem.getIconRes() =
        if (navState.intValue == index) iconRes.selected else iconRes.unSelected

    fun showDialog() {
        dialogVisible.value = true
    }
    fun hideDialog() {
        dialogVisible.value = false
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            when (navState.intValue) {
                0 -> {
                    val foldersViewModel = viewModel(session.office) {
                        factory.foldersViewModel()
                    }
                    FoldersContentTopBar(
                        onAddClick = { showDialog() },
                        onNotificationsClick = { foldersViewModel.navigateToNotifications() },
                        onSearchClick = { foldersViewModel.navigateToSearch() }
                    )
                }

                1 -> {
                    val basketViewModel = viewModel(session.office) {
                        factory.basketViewModel()
                    }
                    val state = basketViewModel.uiState.collectAsState()

                    BasketScreenTopBar(
                        state = state.value,
                        interactor = basketViewModel
                    )
                }

                2 -> ProfileTopBar()
            }
        },
        bottomBar = {
            NavigationBar {
                navItems.forEach {
                    NavigationBarItem(
                        selected = it.index == navState.intValue,
                        onClick = {
                            if (navState.intValue != it.index) {
                                navState.intValue = it.index
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(it.getIconRes()),
                                contentDescription = it.name
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        val m = Modifier.scaffoldPadding(paddingValues)

        when (navState.intValue) {
            0 -> {
                val foldersViewModel = viewModel(session.office) {
                    factory.foldersViewModel()
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

            1 -> {
                val basketViewModel = viewModel(session.office) {
                    factory.basketViewModel()
                }
                val state = basketViewModel.uiState.collectAsState()

                BasketContent(
                    modifier = m,
                    state = state.value,
                    interactor = basketViewModel
                )
            }

            2 -> {
                val profileViewModel = viewModel(session.office) {
                    factory.profileViewModel()
                }
                val state = profileViewModel.uiState.collectAsState()

                ProfileContent(
                    modifier = m,
                    session = session,
                    state = state.value,
                    interactor = profileViewModel,
                    sessionManager = sessionManager
                )
            }
        }
    }
}
