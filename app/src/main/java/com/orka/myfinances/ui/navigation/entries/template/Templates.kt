package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.templates.TemplatesScreen
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel

fun templatesEntry(
    modifier: Modifier,
    destination: Destination.Templates,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {

    TemplatesScreen(
        modifier = modifier,
        viewModel = destination.viewModel as TemplatesScreenViewModel,
        navigationManager = navigationManager
    )
}