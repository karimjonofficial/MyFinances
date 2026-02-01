package com.orka.myfinances.ui.navigation.entries.product

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.products.add.AddProductScreen

fun addProductEntry(
    modifier: Modifier = Modifier,
    destination: Destination.AddProduct,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = "${destination.category.id}") { factory.addProductViewModel() }
    val uiState = viewModel.uiState.collectAsState()

    AddProductScreen(
        modifier = modifier,
        category = destination.category,
        state = uiState.value,
        viewModel = viewModel,
        navigator = navigator
    )
}