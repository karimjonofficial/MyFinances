package com.orka.myfinances.lib.ui.components.lazy.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    items: List<T>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    arrangementSpace: Dp = 0.dp,
    header: (LazyListScope.() -> Unit)? = null,
    footer: (LazyListScope.() -> Unit)? = null,
    item: @Composable ((T) -> Unit)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(arrangementSpace)
    ) {
        if(header != null) header()

        items(items = items) {
            item(it)
        }

        if(footer != null) footer()
    }
}