package com.orka.myfinances.ui.navigation.entries.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.components.StickyHeader
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectTemplateBottomSheet(
    modifier: Modifier = Modifier,
    state: State<ChunkMapState<TemplateItemModel>>,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onSelected: (TemplateItemModel) -> Unit,
    onLoadMore: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        val lazyState = rememberLazyListState()

        LaunchedEffect(lazyState, state) {
            snapshotFlow { lazyState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastIndex ->
                    val totalItems = lazyState.layoutInfo.totalItemsCount
                    if (lastIndex != null && totalItems > 0 && lastIndex >= totalItems - 5) {
                        if (state !is State.Loading && state.value?.nextPageIndex != null) {
                            onLoadMore()
                        }
                    }
                }
        }

        if (state.value != null) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = lazyState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.value!!.content.forEach { (key, items) ->
                    if (key.isNotEmpty()) {
                        item {
                            StickyHeader(key = key)
                        }
                    }

                    items(items = items) { item ->
                        Button(onClick = { onSelected(item) }) {
                            Text(text = item.name)
                        }
                    }
                }
            }
        }
    }
}
