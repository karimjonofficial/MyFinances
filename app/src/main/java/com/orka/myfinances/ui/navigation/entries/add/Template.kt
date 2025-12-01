package com.orka.myfinances.ui.navigation.entries.add

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreen
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel

fun addTemplateEntry(
    modifier: Modifier,
    destination: Destination.AddTemplate,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(modifier, destination) {

    AddTemplateScreen(
        modifier = modifier,
        types = destination.types,
        viewModel = destination.viewModel as AddTemplateScreenViewModel,
        navigationManager = navigationManager
    )
}