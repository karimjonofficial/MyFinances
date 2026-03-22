package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.ui.viewmodel.extensions.isInitial

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
            action = if (state.isInitial()) viewModel::initialize else null
        )

        is State.Success -> {
            @Suppress("UNCHECKED_CAST")
            val groupedItems = state.value
            LazyColumnWithStickHeader(
                modifier = modifier,
                contentPadding = contentPadding,
                map = groupedItems,
                arrangementSpace = arrangementSpace,
                item = item
            )
        }

        is State.Failure<*> -> FailureScreen(
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
    item: @Composable (modifier: Modifier, item: T) -> Unit
) {
    if (state.value != null) {
        LazyColumnWithStickHeader(
            modifier = modifier,
            contentPadding = contentPadding,
            map = state.value!!.content,
            arrangementSpace = arrangementSpace,
            footer = {
                if (state.value!!.nextPageIndex != null)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = viewModel::loadMore) {
                            Text(text = stringResource(R.string.load_more))
                        }
                    }
            },
            item = item
        )
    } else {
        if (state is State.Loading) {
            LoadingScreen(
                modifier = modifier,
                message = state.message.str(),
                action = if (state.isInitial()) viewModel::initialize else null
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