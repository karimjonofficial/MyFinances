package com.orka.myfinances.lib.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import androidx.compose.runtime.State as RState

@Composable
fun <TLoading, TSuccess, TFailure> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
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
            viewModel = viewModel,
            item = item
        )
    }
}

@Composable
fun <TLoading, TSuccess, TFailure> LazyColumnScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
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
            viewModel = viewModel,
            item = item
        )

        if (dialogState.value) {
            dialog()
        }
    }
}

