package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenProductsState
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    viewModel: WarehouseScreenViewModel,
    state: WarehouseScreenProductsState,
    navigationManager: NavigationManager
) {
    when (state) {
        WarehouseScreenProductsState.Loading -> {
            LoadingScreen(modifier)
        }

        WarehouseScreenProductsState.Failure -> {
            FailureScreen(
                modifier = modifier,
                retry = { viewModel.initialize() }
            )
        }

        is WarehouseScreenProductsState.Success -> {
            ProductsList(
                modifier = modifier,
                contentPadding = contentPadding,
                products = state.products,
                onProductClick = { navigationManager.navigateToProduct(it) }
            )
        }
    }
}