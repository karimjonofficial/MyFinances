package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseState
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.flow

@Composable
fun StockItemsContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    viewModel: WarehouseScreenViewModel,
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
        val client = HttpClient()
        val viewModel = viewModel {
            WarehouseScreenViewModel(
                id = category1.id,
                client = client,
                basketRepository = BasketRepository(client = client),
                productTitleEvents = flow {},
                stockEvents = flow {},
                navigator = DummyNavigator(),
                logger = DummyLogger(),
                formatPrice = {""},
                formatDecimal = {""}
            )
        }

        StockItemsContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentPadding = PaddingValues(),
            viewModel = viewModel,
            state = WarehouseState.Success(category1, listOf()),
            onStockItemClick = {}
        )
    }
}