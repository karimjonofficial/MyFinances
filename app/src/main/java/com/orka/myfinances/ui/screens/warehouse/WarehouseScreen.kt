package com.orka.myfinances.ui.screens.warehouse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.warehouse.parts.ProductTitlesContent
import com.orka.myfinances.ui.screens.warehouse.parts.StockItemsContent
import com.orka.myfinances.ui.screens.warehouse.parts.WarehouseScreenTopBar
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    category: Category,
    viewModel: WarehouseScreenViewModel,
    navigator: Navigator
) {
    val productsState = viewModel.productTitlesState.collectAsState()
    val warehouseState = viewModel.warehouseState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            WarehouseScreenTopBar(
                category = category,
                onAddProductClick = { navigator.navigateToAddProduct(it) },
                onAddStockItemClick = { navigator.navigateToAddStockItem(it) }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.scaffoldPadding(paddingValues)) {
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
                StockItemsContent(
                    modifier = m,
                    state = warehouseState.value,
                    viewModel = viewModel,
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                    onStockItemClick = viewModel::addToBasket
                )
            } else {
                ProductTitlesContent(
                    modifier = m,
                    contentPadding = PaddingValues(0.dp),
                    state = productsState.value,
                    viewModel = viewModel,
                    navigator = navigator
                )
            }
        }
    }
}