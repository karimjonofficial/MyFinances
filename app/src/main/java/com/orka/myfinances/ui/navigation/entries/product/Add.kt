package com.orka.myfinances.ui.navigation.entries.product

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.navigation.destination.ProductTitleDestinations
import com.orka.myfinances.ui.screens.product.add.AddProductTitleScreen

fun addProductEntry(
    modifier: Modifier = Modifier,
    destination: ProductTitleDestinations.Add,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "${destination.id}_${session.branchId.value}",
        initializer = { factory.addProductViewModel(categoryId = destination.id) }
    )
    val uiState = viewModel.uiState.collectAsState()

    AddProductTitleScreen(
        modifier = modifier,

        state = uiState.value,
        interactor = viewModel
    )
}