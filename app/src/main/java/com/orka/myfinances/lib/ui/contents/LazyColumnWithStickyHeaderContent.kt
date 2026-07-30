package com.orka.myfinances.lib.ui.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.lazy.column.LazyColumnWithStickyHeader
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.state.State

@Composable
fun <T> LazyColumnWithStickyHeaderContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 0.dp,
    header: (LazyListScope.() -> Unit)? = null,
    footer: (LazyListScope.() -> Unit)? = null,
    state: State<Map<String, List<T>>>,
    refresh: () -> Unit,
    item: @Composable (item: T) -> Unit
) {
    when (state) {
        is State.Loading -> LoadingScreen(
            modifier = modifier,
        )

        is State.Success -> {
            val groupedItems = state.value

            PullToRefreshBox(
                modifier = modifier,
                isRefreshing = false,
                onRefresh = refresh
            ) {
                LazyColumnWithStickyHeader(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding,
                    map = groupedItems,
                    arrangementSpace = arrangementSpace,
                    header = header,
                    footer = footer,
                    item = item
                )
            }
        }

        is State.Failure -> FailureScreen(
            modifier = modifier,
            retry = refresh
        )
    }
}

@Composable
fun <T> LazyColumnWithStickyHeaderContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    arrangementSpace: Dp = 0.dp,
    state: State<ChunkUiModel<T>>,
    loadMore: () -> Unit,
    refresh: () -> Unit,
    threshold: Int = 5,
    item: @Composable (item: T) -> Unit
) {
    val listState = rememberLazyListState()

    if (state.value != null) {
        PullToRefreshBox(
            modifier = modifier,
            isRefreshing = state is State.Loading,
            onRefresh = refresh
        ) {
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

            LazyColumnWithStickyHeader(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                map = state.value!!.content,
                arrangementSpace = arrangementSpace,
                listState = listState,
                footer = {
                    if (state.value!!.nextPageIndex != null && state is State.Loading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                },
                item = item
            )
        }
    } else {
        if (state is State.Loading) {
            LoadingScreen(
                modifier = modifier
            )
        } else {
            FailureScreen(
                modifier = modifier,
                message = stringResource(R.string.unresolved_error),
                retry = if (state is State.Failure) refresh else null
            )
        }
    }
}