package com.orka.myfinances.lib.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.viewmodel.list.ListViewModel
import com.orka.myfinances.lib.viewmodel.list.MapViewModel
import com.orka.myfinances.lib.viewmodel.list.State
import androidx.compose.runtime.State as RState

@Composable
fun <T> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State,
    item: @Composable (Modifier, T) -> Unit,
    viewModel: ListViewModel<T>
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar
    ) { paddingValues ->

        LazyColumnContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            arrangementSpace = arrangementSpace,
            state = state,
            viewModel = viewModel,
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
    dialogState: RState<Boolean>,
    dialog: @Composable () -> Unit,
    item: @Composable (Modifier, T) -> Unit,
    viewModel: ListViewModel<T>
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar
    ) { paddingValues ->

        LazyColumnContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = state,
            arrangementSpace = arrangementSpace,
            viewModel = viewModel,
            item = item
        )

        if (dialogState.value) dialog()
    }
}

@Composable
fun <T> LazyColumnWithStickyHeaderScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    state: State,
    dialogState: RState<Boolean>,
    dialog: @Composable () -> Unit,
    header: @Composable (Modifier, String) -> Unit,
    item: @Composable (Modifier, T) -> Unit,
    viewModel: MapViewModel<T>
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar
    ) { paddingValues ->

        LazyColumnContentWithStickyHeader(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = state,
            arrangementSpace = arrangementSpace,
            viewModel = viewModel,
            header = header,
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
    item: @Composable (Modifier, T) -> Unit,
    viewModel: ListViewModel<T>
) {
    LazyColumnScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = title) }
            )
        },
        state = state,
        item = item,
        arrangementSpace = arrangementSpace,
        viewModel = viewModel
    )
}
