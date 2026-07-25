package com.orka.myfinances.lib.ui.components.lazy.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.StickyHeader

@Composable
fun <T> LazyColumnWithStickyHeader(
    modifier: Modifier = Modifier,
    map: Map<String, List<T>>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    arrangementSpace: Dp = 0.dp,
    listState: LazyListState = rememberLazyListState(),
    header: (LazyListScope.() -> Unit)? = null,
    footer: (LazyListScope.() -> Unit)? = null,
    item: @Composable ((T) -> Unit)
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(arrangementSpace)
    ) {
        if(header != null) header()

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

        if (footer != null) footer()
    }
}