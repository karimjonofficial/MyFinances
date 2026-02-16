package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCard
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockItemUiModel

@Composable
fun StockItemsGrid(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    stockItems: List<StockItemUiModel>,
    onItemClick: (StockItem) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(stockItems.size) { index ->
            StockItemCard(
                modifier = Modifier.fillMaxWidth(),
                item = stockItems[index].model,
                click = { onItemClick(stockItems[index].item) }
            )
        }
    }
}