package com.orka.myfinances.ui.screens.warehouse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.warehouse.parts.ProductsContent
import com.orka.myfinances.ui.screens.warehouse.parts.StockItemsContent
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    viewModel: WarehouseScreenViewModel,
    navigationManager: NavigationManager
) {
    val productsState = viewModel.productsState.collectAsState()
    val warehouseState = viewModel.warehouseState.collectAsState()

    Column(modifier = modifier) {
        val selectedTab = rememberSaveable { mutableIntStateOf(0) }
        val selectedTabValue = selectedTab.intValue

        PrimaryTabRow(selectedTabIndex = selectedTabValue) {

            Tab(
                selected = selectedTabValue == 0,
                onClick = { selectedTab.intValue = 0 },
                text = { Text(text = stringResource(R.string.products)) }
            )

            Tab(
                selected = selectedTabValue == 1,
                onClick = { selectedTab.intValue = 1 },
                text = { Text(text = stringResource(R.string.stock_items)) }
            )
        }


        if (selectedTabValue == 0) {
            ProductsContent(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(0.dp),
                state = productsState.value,
                viewModel = viewModel,
                navigationManager = navigationManager
            )
        } else {
            StockItemsContent(
                modifier = Modifier.fillMaxSize(),
                state = warehouseState.value,
                viewModel = viewModel,
                contentPadding = PaddingValues(horizontal = 8.dp)
            )
        }
    }
}

