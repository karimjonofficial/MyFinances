package com.orka.myfinances.lib.ui.screens

import android.util.Log
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
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun <T> LazyColumnContentWithStickyHeader(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 0.dp,
    state: State<Map<String, List<T>>>,
    viewModel: MapViewModel<T>,
    item: @Composable (modifier: Modifier, item: T) -> Unit
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
            retry = viewModel::initialize
        )
    }
}

@Composable
fun <T> LazyColumnContentWithStickyHeader(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 0.dp,
    state: State<ChunkMapState<T>>,
    viewModel: ChunkViewModel<T>,
    threshold: Int = 5,
    item: @Composable (modifier: Modifier, item: T) -> Unit
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
                        Log.d("LazyColumnWithStickyHeaderContent", "State: $state. Lazy list state: $listState. loadMore executed")
                        viewModel.loadMore()
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
                if (state.value!!.nextPageIndex != null) {
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
                retry = if (state is State.Failure) viewModel::initialize else null
            )
        }
    }
}