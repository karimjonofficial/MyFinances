package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyVerticalGridWithStickHeader(
    modifier: Modifier = Modifier,
    columns: GridCells,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    map: Map<String, List<T>>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    footer: @Composable (() -> Unit)? = null,
    item: @Composable ((T) -> Unit)
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = columns,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
        contentPadding = contentPadding,
    ) {
        map.forEach { (key, items) ->
            stickyHeader {
                StickyHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    key = key
                )
            }

            items(items = items) { item ->
                item(item)
            }
        }

        if (footer != null) {
            item {
                footer()
            }
        }
    }
}