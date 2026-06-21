package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun <T: BottomSheetItemModel> SelectionBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    state: State<ChunkUiModel<T>>,
    selectedItem: T?,
    onSelected: (T) -> Unit,
    onLoadMore: () -> Unit,
    onSearch: (String) -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        val lazyState = rememberLazyListState()
        val searchText = rememberSaveable { mutableStateOf("") }

        LaunchedEffect(Unit) {
            snapshotFlow { searchText.value }
                .drop(1)
                .debounce(300.milliseconds)
                .collect { onSearch(it) }
        }

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
                val itemModifier = Modifier.fillMaxWidth()

                item {
                    VerticalSpacer(8)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.search),
                                contentDescription = null
                            )
                        },
                        label = { Text(text = stringResource(R.string.search)) },
                        value = searchText.value,
                        onValueChange = { searchText.value = it }
                    )
                }

                item { VerticalSpacer(16) }

                if (selectedItem != null) {

                    item(key = "selected") {
                        StickyHeader(key = stringResource(R.string.selected_string))
                    }

                    item(key = "selected_item") {
                        BottomSheetItem(
                            modifier = itemModifier,
                            item = selectedItem,
                            selected = true,
                            onClick = { onDismissRequest() }
                        )
                    }
                }
                state.value!!.content.forEach { (key, items) ->
                    if (key.isNotEmpty()) {
                        item(key = key) {
                            StickyHeader(key = key)
                        }
                    }

                    items(
                        items = items,
                        key = { it.id.value }
                    ) { item ->
                        BottomSheetItem(
                            modifier = itemModifier,
                            item = item,
                            selected = item == selectedItem,
                            onClick = { onSelected(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun <T: BottomSheetItemModel> BottomSheetItem(
    modifier: Modifier = Modifier,
    item: T,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() }.padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            maxLines = 1,
            color = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal
        )

        if(selected) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}