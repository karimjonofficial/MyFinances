package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.basket.BasketContent
import com.orka.myfinances.ui.screens.basket.BasketScreenTopBar
import com.orka.myfinances.ui.screens.folder.home.FoldersContent
import com.orka.myfinances.ui.screens.folder.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentTopBar
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.profile.ProfileContent
import com.orka.myfinances.ui.screens.profile.ProfileTopBar

fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    session: Session,
    sessionManager: SessionManager,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val officeId = session.office.id.toString()
    var dialogVisible by rememberSaveable { mutableStateOf(false) }

    val foldersViewModel = viewModel(key = "folders_$officeId") {
        factory.foldersViewModel()
    }
    val basketViewModel = viewModel(key = "basket_$officeId") {
        factory.basketViewModel()
    }
    val profileViewModel = viewModel(key = "profile_$officeId") {
        factory.profileViewModel()
    }

    HomeScreen(
        modifier = modifier,
        topBar = { index ->
            when (index) {
                0 -> {
                    FoldersContentTopBar(
                        onAddClick = { dialogVisible = true },
                        onNotificationsClick = foldersViewModel::navigateToNotifications,
                        onSearchClick = foldersViewModel::navigateToSearch
                    )
                }

                1 -> {
                    val basketState = basketViewModel.uiState.collectAsState()

                    BasketScreenTopBar(
                        state = basketState.value,
                        interactor = basketViewModel
                    )
                }

                2 -> ProfileTopBar()
            }
        },
        content = { contentModifier, index ->
            when (index) {
                0 -> {
                    val foldersState = foldersViewModel.uiState.collectAsState()
                    val dialogState = foldersViewModel.dialogState.collectAsState()

                    LaunchedEffect(officeId) {
                        foldersViewModel.initialize()
                    }

                    FoldersContent(
                        modifier = contentModifier,
                        state = foldersState.value,
                        interactor = foldersViewModel
                    )

                    if (dialogVisible) {
                        AddFolderDialog(
                            state = dialogState.value,
                            dismissRequest = { dialogVisible = false },
                            onAddTemplateClick = {
                                foldersViewModel.navigateToAddTemplate()
                                dialogVisible = false
                            },
                            onSuccess = { name, type, templateId ->
                                foldersViewModel.addFolder(name, type, templateId)
                                dialogVisible = false
                            },
                            onCancel = { dialogVisible = false }
                        )
                    }
                }

                1 -> {
                    val basketState = basketViewModel.uiState.collectAsState()

                    LaunchedEffect(officeId) {
                        basketViewModel.initialize()
                    }

                    BasketContent(
                        modifier = contentModifier,
                        state = basketState.value,
                        interactor = basketViewModel
                    )
                }

                2 -> {
                    val profileState = profileViewModel.uiState.collectAsState()

                    LaunchedEffect(officeId) {
                        profileViewModel.initialize()
                    }

                    ProfileContent(
                        modifier = contentModifier,
                        session = session,
                        state = profileState.value,
                        interactor = profileViewModel,
                        sessionManager = sessionManager
                    )
                }
            }
        }
    )
}
