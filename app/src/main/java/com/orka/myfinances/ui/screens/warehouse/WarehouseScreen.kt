package com.orka.myfinances.ui.screens.warehouse

import android.util.Log
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
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.warehouse.parts.ProductTitlesContent
import com.orka.myfinances.ui.screens.warehouse.parts.StockContent
import com.orka.myfinances.ui.screens.warehouse.parts.WarehouseScreenTopBar
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductsState
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenInteractor
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenModel

@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    state: State,
    interactor: WarehouseScreenInteractor
) {
    StatefulScreen<WarehouseScreenModel>(
        modifier = modifier,
        state = state,
        onInitialize = {
            Log.d("WarehouseScreen", "Initialize called in line 36")
            interactor.initialize()
        },
        onRetry = {
            Log.d("WarehouseScreen", "Retry called in line 39")
            interactor.initialize()
        },
        topBar = {
            WarehouseScreenTopBar(
                title = if (state is State.Success<*>)
                    (state.value as WarehouseScreenModel).title
                else stringResource(R.string.loading),
                onAddProductClick = { interactor.addProduct() },
                onAddReceive = { interactor.receive() }
            )
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
            val m = Modifier
                .weight(1f)
                .fillMaxWidth()

            if (selectedTabValue == 0) {
                StockContent(
                    modifier = m,
                    state = state,
                    interactor = interactor,
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                    onStockItemClick = interactor::addToBasket
                )
            } else {
                ProductTitlesContent(
                    modifier = m,
                    contentPadding = PaddingValues(0.dp),
                    state = ProductsState.Success(screenState.productTitles),
                    viewModel = interactor
                )
            }
        }
    }
}