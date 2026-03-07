package com.orka.myfinances.ui.screens.warehouse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.TopAppBar
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.warehouse.parts.ProductTitlesContent
import com.orka.myfinances.ui.screens.warehouse.parts.StockItemsContent
import com.orka.myfinances.ui.screens.warehouse.parts.WarehouseScreenTopBar
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductsState
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenState
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseState

@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    state: State,
    viewModel: WarehouseScreenViewModel
) {
    StatefulScreen<WarehouseScreenState>(
        modifier = modifier,
        state = state,
        onRetry = { viewModel.initialize() },
        topBar = {
            val category = (state as? State.Success<*>)?.let { (it.value as WarehouseScreenState).category }
            if (category != null) {
                WarehouseScreenTopBar(
                    category = category,
                    onAddProductClick = { viewModel.addProduct(it) },
                    onAddReceive = { viewModel.receive(it) }
                )
            } else {
                TopAppBar(title = "")
            }
        }
    ) { modifier, screenState ->

        Column(modifier = modifier) {
            val selectedTab = rememberSaveable { mutableIntStateOf(0) }
            val selectedTabValue = selectedTab.intValue

            PrimaryTabRow(selectedTabIndex = selectedTabValue) {

                Tab(
                    selected = selectedTabValue == 0,
                    onClick = { selectedTab.intValue = 0 },
                    text = { Text(text = stringResource(R.string.warehouse)) }
                )

                Tab(
                    selected = selectedTabValue == 1,
                    onClick = { selectedTab.intValue = 1 },
                    text = { Text(text = stringResource(R.string.products)) }
                )
            }
            val m = Modifier.weight(1f).fillMaxWidth()

            if (selectedTabValue == 0) {
                StockItemsContent(
                    modifier = m,
                    state = WarehouseState.Success(screenState.category, screenState.stockItems),
                    viewModel = viewModel,
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                    onStockItemClick = viewModel::addToBasket
                )
            } else {
                ProductTitlesContent(
                    modifier = m,
                    contentPadding = PaddingValues(0.dp),
                    state = ProductsState.Success(screenState.productTitles),
                    viewModel = viewModel
                )
            }
        }
    }
}
