package com.orka.myfinances.lib.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import androidx.compose.runtime.State as RState

@Composable
fun <TLoading, TSuccess, TFailure> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    item: @Composable (Modifier, TSuccess) -> Unit,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar
    ) { paddingValues ->
        val state = viewModel.uiState.collectAsState()

        LazyColumnContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            arrangementSpace = arrangementSpace,
            state = state.value,
            viewModel = viewModel,
            item = item
        )
    }
}

@Composable
fun <TLoading, TSuccess, TFailure> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    arrangementSpace: Dp = 0.dp,
    dialogState: RState<Boolean>,
    dialog: @Composable () -> Unit,
    item: @Composable (Modifier, TSuccess) -> Unit,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar
    ) { paddingValues ->
        val state = viewModel.uiState.collectAsState()

        LazyColumnContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = state.value,
            arrangementSpace = arrangementSpace,
            viewModel = viewModel,
            item = item
        )

        if (dialogState.value) {
            dialog()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <TLoading, TSuccess, TFailure> LazyColumnScreen(
    modifier: Modifier = Modifier,
    title: String,
    arrangementSpace: Dp = 0.dp,
    item: @Composable (Modifier, TSuccess) -> Unit,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>
) {
    LazyColumnScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        },
        item = item,
        arrangementSpace = arrangementSpace,
        viewModel = viewModel
    )
}

