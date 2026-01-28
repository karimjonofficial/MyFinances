package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductsState
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    viewModel: WarehouseScreenViewModel,
    state: ProductsState,
    navigator: Navigator
) {
    when (state) {
        ProductsState.Loading -> {
            LoadingScreen(modifier)
        }

        ProductsState.Failure -> {
            FailureScreen(
                modifier = modifier,
                retry = { viewModel.initialize() }
            )
        }

        is ProductsState.Success -> {
            ProductsList(
                modifier = modifier,
                contentPadding = contentPadding,
                products = state.products,
                onProductClick = { navigator.navigateToProduct(it) }
            )
        }
    }
}