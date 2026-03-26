package com.orka.myfinances.lib.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.LazyColumn
import com.orka.myfinances.lib.ui.viewmodel.State

typealias DialogState = androidx.compose.runtime.State<Boolean>

@Composable
fun <T> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable (State<List<T>>) -> Unit = {},
    bottomBar: @Composable (State<List<T>>) -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State<List<T>>,
    refresh: () -> Unit,
    item: @Composable (T) -> Unit,
) {
    StatefulScreen<List<T>>(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        onRetry = refresh,
        state = state
    ) { modifier, items ->
        LazyColumn(
            modifier = modifier,
            items = items,
            arrangementSpace = arrangementSpace,
            item = item
        )
    }
}

@Composable
fun <T> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable (State<List<T>>) -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State<List<T>>,
    refresh: () -> Unit,
    dialogState: DialogState,
    dialog: @Composable () -> Unit,
    item: @Composable (T) -> Unit,
) {
    StatefulScreen<List<T>>(
        modifier = modifier,
        topBar = topBar,
        onRetry = refresh,
        state = state
    ) { modifier, items ->
        LazyColumn(
            modifier = modifier,
            items = items,
            arrangementSpace = arrangementSpace,
            item = item
        )

        if (dialogState.value) dialog()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> LazyColumnScreen(
    modifier: Modifier = Modifier,
    title: String,
    bottomBar: @Composable (State<List<T>>) -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State<List<T>>,
    refresh: () -> Unit,
    item: @Composable (T) -> Unit,
) {
    LazyColumnScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = title) }
            )
        },
        state = state,
        refresh = refresh,
        bottomBar = bottomBar,
        item = item,
        arrangementSpace = arrangementSpace,
    )
}