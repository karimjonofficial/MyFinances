package com.orka.myfinances.lib.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.lib.ui.viewmodel.State

typealias DialogState = androidx.compose.runtime.State<Boolean>

@Composable
fun <T> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State,
    viewModel: ListViewModel<T>,
    item: @Composable (Modifier, T) -> Unit,
) {
    StatefulScreen<List<T>>(
        modifier = modifier,
        topBar = topBar,
        onRetry = { viewModel.initialize() },
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
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State,
    viewModel: ListViewModel<T>,
    dialogState: DialogState,
    dialog: @Composable () -> Unit,
    item: @Composable (Modifier, T) -> Unit,
) {
    StatefulScreen<List<T>>(
        modifier = modifier,
        topBar = topBar,
        onRetry = { viewModel.initialize() },
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
    arrangementSpace: Dp = 0.dp,
    state: State,
    viewModel: ListViewModel<T>,
    item: @Composable (Modifier, T) -> Unit,
) {
    LazyColumnScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = title) }
            )
        },
        state = state,
        viewModel = viewModel,
        item = item,
        arrangementSpace = arrangementSpace,
    )
}

@Composable
fun <T> LazyColumnWithStickyHeaderScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State,
    viewModel: MapViewModel<T>,
    item: @Composable (Modifier, T) -> Unit,
) {
    StatefulScreen<Map<String, List<T>>>(
        modifier = modifier,
        topBar = topBar,
        onRetry = { viewModel.initialize() },
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
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State,
    viewModel: MapViewModel<T>,
    dialogState: DialogState,
    dialog: @Composable () -> Unit,
    item: @Composable (Modifier, T) -> Unit,
) {
    StatefulScreen<Map<String, List<T>>>(
        modifier = modifier,
        topBar = topBar,
        onRetry = { viewModel.initialize() },
        state = state
    ) { modifier, map ->
        LazyColumnWithStickHeader(
            modifier = modifier,
            map = map,
            arrangementSpace = arrangementSpace,
            item = item
        )

        if (dialogState.value) dialog()
    }
}
