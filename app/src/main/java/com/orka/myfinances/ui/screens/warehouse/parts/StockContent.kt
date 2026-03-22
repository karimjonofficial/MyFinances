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
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.ui.viewmodel.extensions.isInitial
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenInteractor
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenModel

@Composable
fun StockContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    interactor: WarehouseScreenInteractor,
    state: State<WarehouseScreenModel>,
    onStockItemClick: (Id) -> Unit,
) {
    when (state) {
        is State.Loading -> LoadingScreen(
            modifier = modifier,
            message = state.message.str(),
            action = if (state.isInitial()) interactor::initialize else null
        )

        is State.Failure -> FailureScreen(
            modifier = modifier,
            retry = interactor::initialize
        )

        is State.Success -> StockItemsGrid(
            modifier = modifier,
            contentPadding = contentPadding,
            stockItems = state.value.stockItems,
            onItemClick = onStockItemClick
        )
    }
}

@Preview
@Composable
private fun StockContentPreview() {
    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = stringResource(R.string.warehouse)
    ) { paddingValues ->
        StockContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentPadding = PaddingValues(),
            interactor = WarehouseScreenInteractor.dummy,
            state = State.Success(
                value = WarehouseScreenModel(
                    title = category1.name,
                    productTitles = listOf(),
                    stockItems = listOf()
                )
            ),
            onStockItemClick = {}
        )
    }
}