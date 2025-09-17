package com.orka.myfinances.ui.screens.warehouse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.ui.screens.home.components.ProductCard

@Composable
fun WarehouseGrid(
    modifier: Modifier = Modifier,
    products: List<Product>,
    stockItems: List<StockItem>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp)
) {
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
            LazyColumn(
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(products.size) { index ->
                    ProductCard(product = products[index]) {}//TODO implement click
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(stockItems.size) { index ->
                    StockItemCard(item = stockItems[index]) {}//TODO implement click
                }
            }
        }
    }
}