package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel

fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(modifier, destination) {
    val uiState = (destination.viewModel as HomeScreenViewModel).uiState.collectAsState()

    HomeScreen(
        modifier = modifier,
        state = uiState.value,
        onNavigateToFolder = { folder ->
            when (folder) {
                is Catalog -> navigationManager.navigateToCatalog(folder)
                is Warehouse -> navigationManager.navigateToWarehouse(folder)
            }
        }
    )
}