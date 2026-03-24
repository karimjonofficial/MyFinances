package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCard
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockItemUiModel

@Composable
fun StockItemsGrid(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    stockItems: List<StockItemUiModel>,
    onItemClick: (Id) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = stockItems) { item ->
            StockItemCard(
                modifier = Modifier.fillMaxWidth(),
                item = item.model,
                onClick = { onItemClick(item.id) }
            )
        }
    }
}