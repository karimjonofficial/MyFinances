package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreen

fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    session: Session,
    sessionManager: SessionManager,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val foldersViewModel = viewModel(key = session.office.id.toString()) { factory.foldersViewModel() }
    val basketViewModel = viewModel(key = session.office.id.toString()) { factory.basketViewModel() }
    val profileViewModel = viewModel(key = session.office.id.toString()) { factory.profileViewModel() }

    HomeScreen(
        modifier = modifier,
        session = session,
        sessionManager = sessionManager,
        foldersInteractor = foldersViewModel,
        foldersState = foldersViewModel.uiState,
        folderDialogState = foldersViewModel.dialogState,
        basketInteractor = basketViewModel,
        basketState = basketViewModel.uiState,
        profileInteractor = profileViewModel,
        profileState = profileViewModel.uiState
    )
}
