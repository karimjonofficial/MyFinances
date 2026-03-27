package com.orka.myfinances.lib.ui.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.components.LazyColumnWithStickHeader
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun <T> LazyColumnWithStickyHeaderContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 0.dp,
    state: State<Map<String, List<T>>>,
    refresh: () -> Unit,
    item: @Composable (item: T) -> Unit
) {
    when (state) {
        is State.Loading -> LoadingScreen(
            modifier = modifier,
            message = state.message.str(),
        )

        is State.Success -> {
            val groupedItems = state.value
            LazyColumnWithStickHeader(
                modifier = modifier,
                contentPadding = contentPadding,
                map = groupedItems,
                arrangementSpace = arrangementSpace,
                item = item
            )
        }

        is State.Failure -> FailureScreen(
            modifier = modifier,
            message = state.error.str(),
            retry = refresh
        )
    }
}

@Composable
fun <T> LazyColumnWithStickyHeaderContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 0.dp,
    state: State<ChunkMapState<T>>,
    loadMore: () -> Unit,
    refresh: () -> Unit,
    threshold: Int = 5,
    item: @Composable (item: T) -> Unit
) {
    if (state.value != null) {
        val listState = rememberLazyListState()

        LaunchedEffect(listState, state) {
            snapshotFlow {
                val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                val totalItems = listState.layoutInfo.totalItemsCount
                lastVisible to totalItems
            }.collect { (lastVisible, totalItems) ->
                if (lastVisible != null && totalItems - lastVisible <= threshold && state is State.Success) {
                    if (state.value.nextPageIndex != null) {
                        loadMore()
                    }
                }
            }
        }

        LazyColumnWithStickHeader(
            modifier = modifier,
            contentPadding = contentPadding,
            map = state.value!!.content,
            arrangementSpace = arrangementSpace,
            listState = listState,
            footer = {
                if (state.value!!.nextPageIndex != null && state is State.Loading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            },
            item = item
        )
    } else {
        if (state is State.Loading) {
            LoadingScreen(
                modifier = modifier,
                message = state.message.str()
            )
        } else {
            FailureScreen(
                modifier = modifier,
                message = if (state is State.Failure) state.error.str() else stringResource(R.string.unresolved_error),
                retry = if (state is State.Failure) refresh else null
            )
        }
    }
}