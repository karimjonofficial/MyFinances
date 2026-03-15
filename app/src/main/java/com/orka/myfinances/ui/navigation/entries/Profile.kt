package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.home.ProfileContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.orka.myfinances.lib.viewmodel.viewModel

fun profileEntry(
    modifier: Modifier,
    destination: Destination.Profile,
    session: Session,
    sessionManager: SessionManager,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val profileViewModel = viewModel(session.office) {
        factory.profileViewModel()
    }
    LaunchedEffect(session) {
        profileViewModel.initialize()
    }
    val state = profileViewModel.uiState.collectAsState()

    ProfileContent(
        modifier = modifier,
        session = session,
        state = state.value,
        interactor = profileViewModel,
        sessionManager = sessionManager
    )
}
