package com.orka.myfinances.ui.navigation.entries.add

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.products.add.AddProductScreen
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductScreenViewModel

fun addProductEntry(
    modifier: Modifier = Modifier.Companion,
    destination: Destination.AddProduct,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {
    val uiState = (destination.viewModel as AddProductScreenViewModel).uiState.collectAsState()

    AddProductScreen(
        modifier = modifier,
        warehouse = destination.warehouse,
        state = uiState.value,
        viewModel = destination.viewModel,
        navigationManager = navigationManager
    )
}