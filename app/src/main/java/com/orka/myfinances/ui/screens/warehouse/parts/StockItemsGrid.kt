package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCard

@Composable
fun StockItemsGrid(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    stockItems: List<StockItem>
) {
    LazyVerticalGrid(
        modifier = modifier,
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