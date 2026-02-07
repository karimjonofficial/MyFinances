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
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.ui.viewmodel.viewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.home.parts.BasketScreenTopBar
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar
import com.orka.myfinances.ui.screens.home.parts.ProfileTopBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    session: Session,
    sessionManager: SessionManager,
    factory: Factory,
    navigator: Navigator,
    selectFolder: (Folder) -> Unit
) {
    val navItems = listOf(
        NavItem(
            index = 0,
            name = stringResource(R.string.home),
            iconRes = IconRes(
                selected = R.drawable.home_filled,
                unSelected = R.drawable.home_outlined
            )
        ),
        NavItem(
            index = 1,
            name = stringResource(R.string.basket),
            iconRes = IconRes(
                unSelected = R.drawable.shopping_cart_outlined,
                selected = R.drawable.shopping_cart_filled
            )
        ),
        NavItem(
            index = 2,
            name = stringResource(R.string.profile),
            iconRes = IconRes(
                unSelected = R.drawable.account_circle_outlined,
                selected = R.drawable.account_circle_filled
            )
        )
    )
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
                0 -> HomeScreenTopBar(
                    onAddClick = { showDialog() },
                    onNotificationsClick = { navigator.navigateToNotifications() },
                    onSearchClick = { navigator.navigateToSearch() }
                )

                1 -> {
                    val viewModel = viewModel(session.office) {
                        factory.basketViewModel()
                    }
                    BasketScreenTopBar(viewModel = viewModel)
                }
                2 -> ProfileTopBar(user = session.user)
            }
        },
        bottomBar = {
            NavigationBar {
                navItems.forEach {
                    NavigationBarItem(
                        selected = it.index == navState.intValue,
                        onClick = {
                            if (navState.intValue != it.index)
                                navState.intValue = it.index
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
                val viewModel = viewModel(session.office) {
                    factory.foldersViewModel()
                }
                val state = viewModel.uiState.collectAsState()

                FoldersContent(
                    modifier = m,
                    state = state.value,
                    onNavigateToFolder = selectFolder
                )

                if (dialogVisible.value) {
                    val dialogState = viewModel.dialogState.collectAsState()

                    AddFolderDialog(
                        state = dialogState.value,
                        dismissRequest = { hideDialog() },
                        onAddTemplateClick = {
                            navigator.navigateToAddTemplate()
                            hideDialog()
                        },
                        onSuccess = { name, type, templateId ->
                            viewModel.addFolder(name, type, templateId)
                            hideDialog()
                        },
                        onCancel = { hideDialog() }
                    )
                }
            }

            1 -> {
                val viewModel = viewModel(session.office) {
                    factory.basketViewModel()
                }
                val state = viewModel.uiState.collectAsState()

                BasketContent(
                    modifier = m,
                    state = state.value,
                    viewModel = viewModel,
                    navigator = navigator
                )
            }

            2 -> {
                val viewModel = viewModel(session.office) {
                    factory.profileViewModel()
                }
                val state = viewModel.uiState.collectAsState()

                ProfileContent(
                    modifier = m,
                    session = session,
                    state = state.value,
                    navigator = navigator,
                    sessionManager = sessionManager
                )
            }
        }
    }
}