package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreen

fun addReceiveEntry(
    modifier: Modifier,
    destination: Destination.AddStockItem,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = "${destination.id}") {
        factory.addReceiveViewModel(destination.id)
    }
    val productTitleBottomSheetViewModel = viewModel(key = "product-title-sheet-${destination.id}") {
        factory.productTitleBottomSheetViewModel(destination.id)
    }
    val state = viewModel.uiState.collectAsState()
    val productTitleSheetState = productTitleBottomSheetViewModel.uiState.collectAsState()

    LaunchedEffect(destination.id) {
        productTitleBottomSheetViewModel.initialize()
    }

    AddReceiveScreen(
        modifier = modifier,
        interactor = viewModel,
        state = state.value,
        productTitleSheetState = productTitleSheetState.value,
        productTitleSheetInteractor = productTitleBottomSheetViewModel
    )
}
