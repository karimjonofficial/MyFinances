package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.catalog.CatalogScreen
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel

fun catalogEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    navigator: Navigator
): NavEntry<Destination> = entry(destination) {

    Scaffold(
        modifier = modifier,
        title = destination.catalog.name
    ) { paddingValues ->
        val viewModel = destination.viewModel as CatalogScreenViewModel
        val uiState = viewModel.uiState.collectAsState()

        CatalogScreen(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = uiState.value,
            viewModel = viewModel,
            navigator = navigator
        )
    }
}