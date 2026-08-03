package com.orka.myfinances.ui.navigation.entries.settings

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.navigation.destination.SettingsDestinations
import com.orka.myfinances.ui.screens.settings.main.SettingsScreen

fun settingsEntry(
    modifier: Modifier,
    destination: SettingsDestinations.Main,
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