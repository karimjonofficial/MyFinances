package com.orka.myfinances.ui.navigation.entries.product

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.product.edit.EditProductTitleScreen

fun editProductEntry(
    modifier: Modifier = Modifier,
    destination: Destination.EditProduct,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "edit-${destination.id.value}",
        initializer = { factory.editProductViewModel(destination.id) }
    )
    val uiState = viewModel.uiState.collectAsState()

    EditProductTitleScreen(
        modifier = modifier,
        state = uiState.value,
        interactor = viewModel
    )
}
