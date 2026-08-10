package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.DebtDestinations
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.debt.details.DebtScreen

fun debtEntry(
    modifier: Modifier = Modifier,
    destination: DebtDestinations.Details,
    session: Session,
    factory: Factory,
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "${destination.id.value}_${session.branchId.value}",
        initializer = { factory.debtViewModel(destination.id) }
    )
    val state = viewModel.uiState.collectAsState()

    DebtScreen(
        modifier = modifier,
        state = state.value,
        interactor = viewModel
    )
}