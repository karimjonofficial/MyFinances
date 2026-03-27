package com.orka.myfinances.lib.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.LazyColumnWithStickHeader
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun <T> LazyColumnWithStickyHeaderScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable (State<Map<String, List<T>>>) -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State<Map<String, List<T>>>,
    refresh: () -> Unit,
    item: @Composable (T) -> Unit,
) {
    StatefulScreen<Map<String, List<T>>>(
        modifier = modifier,
        topBar = topBar,
        onRetry = refresh,
        state = state
    ) { modifier, map ->
        LazyColumnWithStickHeader(
            modifier = modifier,
            map = map,
            arrangementSpace = arrangementSpace,
            item = item
        )
    }
}

@Composable
fun <T> LazyColumnWithStickyHeaderScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable (State<Map<String, List<T>>>) -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State<Map<String, List<T>>>,
    refresh: () -> Unit,
    dialog: @Composable () -> Unit,
    dialogVisible: Boolean,
    item: @Composable (T) -> Unit,
) {
    StatefulScreen<Map<String, List<T>>>(
        modifier = modifier,
        topBar = topBar,
        onRetry = refresh,
        state = state
    ) { modifier, map ->

        LazyColumnWithStickHeader(
            modifier = modifier,
            map = map,
            arrangementSpace = arrangementSpace,
            item = item
        )

        if (dialogVisible) dialog()
    }
}