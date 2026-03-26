package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyColumn(
    modifier: Modifier = Modifier,
    items: List<T>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    arrangementSpace: Dp = 0.dp,
    item: @Composable ((T) -> Unit)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(arrangementSpace)
    ) {
        items(items = items) {
            item(it)
        }
    }
}