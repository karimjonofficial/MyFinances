package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyColumnWithStickHeader(
    modifier: Modifier = Modifier,
    map: Map<String, List<T>>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    arrangementSpace: Dp = 0.dp,
    footer: @Composable (() -> Unit)? = null,
    item: @Composable ((Modifier, T) -> Unit)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(arrangementSpace)
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
                item(Modifier.fillMaxWidth(), item)//TODO get rid of modifier
            }
        }

        if (footer != null) {
            item {
                footer()
            }
        }
    }
}

@Composable
fun <T> LazyColumnWithStickHeader(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    map: Map<String, List<T>>,
    isRefreshing: Boolean,
    pullToRefresh: () -> Unit,
    arrangementSpace: Dp = 0.dp,
    footer: @Composable (() -> Unit)? = null,
    item: @Composable ((Modifier, T) -> Unit)
) {
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = pullToRefresh
    ) {
        LazyColumnWithStickHeader(
            contentPadding = contentPadding,
            map = map,
            arrangementSpace = arrangementSpace,
            footer = footer,
            item = item
        )
    }
}