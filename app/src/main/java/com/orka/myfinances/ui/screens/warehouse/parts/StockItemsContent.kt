package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseState

@Composable
fun StockItemsContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    viewModel: WarehouseScreenViewModel,
    state: WarehouseState,
    onStockItemClick: (StockItem) -> Unit,
) {
    when (state) {
        is WarehouseState.Loading -> {
            LoadingScreen(modifier)
        }

        is WarehouseState.Failure -> {
            FailureScreen(
                modifier = modifier,
                retry = { viewModel.initialize() }
            )
        }

        is WarehouseState.Success -> {
            StockItemsGrid(
                modifier = modifier,
                contentPadding = contentPadding,
                stockItems = state.stockItems,
                onItemClick = onStockItemClick
            )
        }
    }
}