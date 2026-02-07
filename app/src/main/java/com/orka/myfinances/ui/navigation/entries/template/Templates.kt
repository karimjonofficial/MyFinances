package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.TemplatesScreen

fun templatesEntry(
    modifier: Modifier,
    destination: Destination.Templates,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    TemplatesScreen(
        modifier = modifier,
        viewModel = viewModel { factory.templatesViewModel() },
        navigator = navigator
    )
}