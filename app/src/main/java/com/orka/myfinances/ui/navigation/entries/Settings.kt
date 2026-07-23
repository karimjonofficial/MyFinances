package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.settings.main.SettingsScreen

fun settingsEntry(
    modifier: Modifier,
    destination: Destination.Settings,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel {
        factory.settingsViewModel()
    }
    val state = viewModel.uiState.collectAsState()

    SettingsScreen(
        modifier = modifier,
        state = state.value,
        interactor = viewModel
    )
}