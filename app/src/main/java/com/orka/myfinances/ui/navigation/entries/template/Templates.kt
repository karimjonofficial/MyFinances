package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.templates.list.TemplatesScreen

fun templatesEntry(
    modifier: Modifier,
    destination: Destination.Templates,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel {
        factory.templatesViewModel()
    }
    val state = viewModel.uiState.collectAsState()
    TemplatesScreen(
        modifier = modifier,
        interactor = viewModel,
        state = state.value
    )
}