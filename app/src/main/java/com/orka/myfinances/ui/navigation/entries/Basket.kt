package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.home.BasketContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.orka.myfinances.lib.viewmodel.viewModel

fun basketEntry(
    modifier: Modifier,
    destination: Destination.Basket,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val basketViewModel = viewModel(session.office) {
        factory.basketViewModel()
    }
    LaunchedEffect(session) {
        basketViewModel.initialize()
    }
    val state = basketViewModel.uiState.collectAsState()

    BasketContent(
        modifier = modifier,
        state = state.value,
        interactor = basketViewModel
    )
}
