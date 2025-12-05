package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.templates.TemplatesScreen
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel

fun templatesEntry(
    modifier: Modifier,
    destination: Destination.Templates
): NavEntry<Destination> = entry(destination) {
    val uiState = (destination.viewModel as TemplatesScreenViewModel).uiState.collectAsState()

    TemplatesScreen(modifier, uiState.value)
}