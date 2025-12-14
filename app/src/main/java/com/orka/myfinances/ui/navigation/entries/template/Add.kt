package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreen
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenViewModel

fun addTemplateEntry(
    modifier: Modifier,
    destination: Destination.AddTemplate,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {

    AddTemplateScreen(
        modifier = modifier,
        types = destination.types,
        viewModel = destination.viewModel as AddTemplateScreenViewModel,
        navigationManager = navigationManager
    )
}