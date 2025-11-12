package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.lib.ui.FailureScreen
import com.orka.myfinances.lib.ui.LoadingScreen
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenWarehouseState

@Composable
fun StockItemsContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    viewModel: WarehouseScreenViewModel,
    state: WarehouseScreenWarehouseState,
    onStockItemClick: (StockItem) -> Unit,
) {
    when (state) {
        is WarehouseScreenWarehouseState.Loading -> {
            LoadingScreen(modifier)
        }

        is WarehouseScreenWarehouseState.Failure -> {
            FailureScreen(
                modifier = modifier,
                retry = { viewModel.initialize() }
            )
        }

        is WarehouseScreenWarehouseState.Success -> {
            StockItemsGrid(
                modifier = modifier,
                contentPadding = contentPadding,
                stockItems = state.stockItems,
                onItemClick = onStockItemClick
            )
        }
    }
}