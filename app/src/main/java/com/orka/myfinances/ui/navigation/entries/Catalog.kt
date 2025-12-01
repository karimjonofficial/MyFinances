package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.catalog.CatalogScreen
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel

fun catalogEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(modifier, destination) {
    val viewModel = destination.viewModel as CatalogScreenViewModel
    val uiState = viewModel.uiState.collectAsState()

    CatalogScreen(
        modifier = modifier,
        state = uiState.value,
        viewModel = viewModel,
        navigationManager = navigationManager
    )
}