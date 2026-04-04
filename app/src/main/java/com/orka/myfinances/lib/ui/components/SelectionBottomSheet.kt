package com.orka.myfinances.lib.ui.components

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
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.ItemModel
import com.orka.myfinances.lib.ui.viewmodel.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: ItemModel> SelectionBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    state: State<ChunkMapState<T>>,
    selectedItem: T?,
    onSelected: (T) -> Unit,
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
                if (selectedItem != null) {
                    item {
                        StickyHeader(key = stringResource(R.string.selected_string))
                    }

                    item {
                        Button(onClick = { onSelected(selectedItem) }) {
                            Text(text = selectedItem.title)
                        }
                    }
                }
                state.value!!.content.forEach { (key, items) ->
                    if (key.isNotEmpty()) {
                        item {
                            StickyHeader(key = key)
                        }
                    }

                    items(items = items) { item ->
                        Button(onClick = { onSelected(item) }) {
                            Text(text = item.title)
                        }
                    }
                }
            }
        }
    }
}