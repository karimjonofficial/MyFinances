package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.catalog.CatalogScreen
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun catalogEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = destination.catalog.name) }
            )
        }
    ) { paddingValues ->
        val viewModel = destination.viewModel as CatalogScreenViewModel
        val uiState = viewModel.uiState.collectAsState()

        CatalogScreen(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = uiState.value,
            viewModel = viewModel,
            navigationManager = navigationManager
        )
    }
}