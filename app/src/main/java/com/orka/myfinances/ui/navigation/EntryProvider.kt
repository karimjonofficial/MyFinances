package com.orka.myfinances.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.parts.FoldersList
import com.orka.myfinances.ui.screens.template.TemplateScreen
import com.orka.myfinances.ui.screens.warehouse.WarehouseGrid

fun entryProvider(
    modifier: Modifier,
    destination: Destination,
    navigationManager: NavigationManager
): NavEntry<Destination> {
    return when (destination) {
        is Destination.Home -> homeScreenEntry(modifier, destination, navigationManager)
        is Destination.Catalog -> catalogScreenEntry(modifier, destination, navigationManager)
        is Destination.ProductFolder -> productFolderEntry(modifier, destination)
        is Destination.Profile -> NavEntry(key = destination) {}
        is Destination.Notifications -> NavEntry(key = destination) {}
        is Destination.AddTemplate -> addTemplateEntryProvider(modifier, destination, navigationManager)
    }
}

private fun homeScreenEntry(
    modifier: Modifier,
    destination: Destination.Home,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {
    val uiState = destination.viewModel.uiState.collectAsState()

    HomeScreen(
        modifier = modifier,
        state = uiState.value,
        onNavigateToFolder = { folder ->
            when(folder) {
                is Catalog -> navigationManager.navigateToCatalog(folder)
                is ProductFolder -> navigationManager.navigateToProductFolder(folder)
            }
        }
    )
}

private fun productFolderEntry(
    modifier: Modifier,
    destination: Destination.ProductFolder
): NavEntry<Destination> = NavEntry(key = destination) {
    val productFolder = destination.productFolder

    WarehouseGrid(
        modifier = modifier,
        products = productFolder.products,
        stockItems = productFolder.stockItems
    )
}

private fun catalogScreenEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {
    val folder = destination.catalog

    FoldersList(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        items = folder.folders,
        onFolderSelected = { folder ->
            when(folder) {
                is Catalog -> navigationManager.navigateToCatalog(folder)
                is ProductFolder -> navigationManager.navigateToProductFolder(folder)
            }
        }
    )
}

private fun addTemplateEntryProvider(
    modifier: Modifier,
    destination: Destination.AddTemplate,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {

    TemplateScreen(
        modifier = modifier,
        types = destination.types,
        viewModel = destination.viewModel,
        navigationManager = navigationManager
    )
}