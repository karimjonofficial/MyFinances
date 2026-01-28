package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.catalog.CatalogScreen

fun catalogEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = destination.catalog.id.value.toString(),
        initializer = { factory.catalogViewModel(destination.catalog) }
    )
    val uiState = viewModel.uiState.collectAsState()

    CatalogScreen(
        modifier = modifier,
        catalog = destination.catalog,
        state = uiState.value,
        viewModel = viewModel,
        navigator = navigator
    )
}