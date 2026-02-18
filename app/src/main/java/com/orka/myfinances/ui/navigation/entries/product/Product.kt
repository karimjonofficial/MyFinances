package com.orka.myfinances.ui.navigation.entries.product

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.product.ProductTitleScreen

@OptIn(ExperimentalMaterial3Api::class)
fun productTitleEntry(
    modifier: Modifier,
    destination: Destination.ProductTitle,
    factory: Factory,
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel {
        factory.productTitleViewModel(destination.productTitle)
    }
    val state = viewModel.uiState.collectAsState()

    ProductTitleScreen(
        modifier = modifier,
        state = state.value,
        viewModel = viewModel
    )
}