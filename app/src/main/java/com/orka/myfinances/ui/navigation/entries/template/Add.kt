package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.navigation.destination.TemplateDestinations
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreen

fun addTemplateEntry(
    modifier: Modifier,
    destination: TemplateDestinations.Add,
    factory: Factory
): NavEntry<Destination> = entry(destination) {

    AddTemplateScreen(
        modifier = modifier,
        interactor = viewModel { factory.addTemplateViewModel() }
    )
}