package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenInteractor
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseState

@Composable
fun StockItemsContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    viewModel: WarehouseScreenInteractor,
    state: WarehouseState,
    onStockItemClick: (Id) -> Unit,
) {
    when (state) {
        is WarehouseState.Loading -> LoadingScreen(modifier)

        is WarehouseState.Failure -> FailureScreen(
            modifier = modifier,
            retry = viewModel::initialize
        )

        is WarehouseState.Success -> StockItemsGrid(
            modifier = modifier,
            contentPadding = contentPadding,
            stockItems = state.stockItems,
            onItemClick = onStockItemClick
        )
    }
}

@Preview
@Composable
private fun StockItemContentPreview() {
    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = stringResource(R.string.warehouse)
    ) { paddingValues ->
        StockItemsContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentPadding = PaddingValues(),
            viewModel = WarehouseScreenInteractor.dummy,
            state = WarehouseState.Success(category1, listOf()),
            onStockItemClick = {}
        )
    }
}