package com.orka.myfinances.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.NavigationManagerImpl
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.add.product.AddProductScreen
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreen
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.basket.BasketScreen
import com.orka.myfinances.ui.screens.basket.BasketScreenViewModel
import com.orka.myfinances.ui.screens.catalog.CatalogScreen
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreen
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.WarehouseScreen
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

fun entryProvider(
    modifier: Modifier,
    destination: Destination,
    navigationManager: NavigationManagerImpl
): NavEntry<Destination> {
    return when (destination) {
        is Destination.Home -> homeEntry(modifier, destination, navigationManager)
        is Destination.Catalog -> catalogEntry(modifier, destination, navigationManager)
        is Destination.Warehouse -> warehouseEntry(modifier, destination, navigationManager)
        is Destination.Profile -> profileEntry(modifier, destination)
        is Destination.Notifications -> notificationsEntry(modifier, destination)
        is Destination.AddTemplate -> addTemplateEntry(modifier, destination, navigationManager)
        is Destination.Settings -> settingsEntry(modifier, destination, navigationManager)
        is Destination.Templates -> templatesEntry(modifier, destination)
        is Destination.AddProduct -> addProductEntry(modifier, destination, navigationManager)
        is Destination.Product -> NavEntry(destination) {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.product))
            }
        }
        is Destination.Basket -> basketEntry(destination, modifier)
    }
}

private fun basketEntry(
    destination: Destination.Basket,
    modifier: Modifier
): NavEntry<Destination> = NavEntry(destination) {
    val viewModel = destination.viewModel as BasketScreenViewModel
    val uiState = viewModel.uiState.collectAsState()

    BasketScreen(
        modifier = modifier,
        state = uiState.value,
        viewModel = viewModel
    )
}

private fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {
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

private fun warehouseEntry(
    modifier: Modifier,
    destination: Destination.Warehouse,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {
    val viewModel = destination.viewModel as WarehouseScreenViewModel

    WarehouseScreen(
        modifier = modifier,
        viewModel = viewModel,
        navigationManager = navigationManager
    )
}

private fun catalogEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {
    val viewModel = destination.viewModel as CatalogScreenViewModel
    val uiState = viewModel.uiState.collectAsState()

    CatalogScreen(
        modifier = modifier,
        state = uiState.value,
        viewModel = viewModel,
        navigationManager = navigationManager
    )
}

private fun addTemplateEntry(
    modifier: Modifier,
    destination: Destination.AddTemplate,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {

    AddTemplateScreen(
        modifier = modifier,
        types = destination.types,
        viewModel = destination.viewModel as AddTemplateScreenViewModel,
        navigationManager = navigationManager
    )
}

private fun profileEntry(
    modifier: Modifier,
    destination: Destination.Profile,
): NavEntry<Destination> = NavEntry(key = destination) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.profile))
    }
}

private fun settingsEntry(
    modifier: Modifier,
    destination: Destination.Settings,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navigationManager.navigateToTemplates() }) {
            Text(text = stringResource(R.string.templates))
        }
    }
}

private fun templatesEntry(
    modifier: Modifier,
    destination: Destination.Templates
): NavEntry<Destination> = NavEntry(key = destination) {
    val uiState = (destination.viewModel as TemplatesScreenViewModel).uiState.collectAsState()

    TemplatesScreen(modifier, uiState.value)
}

private fun notificationsEntry(
    modifier: Modifier,
    destination: Destination.Notifications
): NavEntry<Destination> = NavEntry(key = destination) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.notifications))
    }
}

private fun addProductEntry(
    modifier: Modifier = Modifier,
    destination: Destination.AddProduct,
    navigationManager: NavigationManager
): NavEntry<Destination> = NavEntry(key = destination) {
    val uiState = (destination.viewModel as AddProductScreenViewModel).uiState.collectAsState()

    AddProductScreen(
        modifier = modifier,
        warehouse = destination.warehouse,
        state = uiState.value,
        viewModel = destination.viewModel,
        navigationManager = navigationManager
    )
}