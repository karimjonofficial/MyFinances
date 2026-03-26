package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.templates.details.TemplateScreen

fun templateEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Template,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = "${destination.id}") {
        factory.templateViewModel(destination.id)
    }
    val uiState = viewModel.uiState.collectAsState()

    TemplateScreen(
        modifier = modifier,
        state = uiState.value
    )
}