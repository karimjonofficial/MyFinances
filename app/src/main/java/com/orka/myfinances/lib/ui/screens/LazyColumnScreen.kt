package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
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

        Screen(
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

        Screen(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = state.value,
            viewModel = viewModel,
            item = item
        )

        if(dialogState.value) {
            dialog()
        }
    }
}

@Composable
private fun <TLoading, TSuccess, TFailure> Screen(
    modifier: Modifier = Modifier,
    state: State<TLoading, List<TSuccess>, TFailure>,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>,
    item: @Composable (modifier: Modifier, item: TSuccess) -> Unit
) {
    when(state) {
        is State.Initial -> { LoadingScreen(modifier) }

        is State.Loading -> {

            LoadingScreen(
                modifier = modifier,
                message = state.message.toString()
            )
        }

        is State.Success -> {

            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = state.value) {
                    item(Modifier.fillMaxWidth(), it)
                }
            }
        }

        is State.Failure -> {

            FailureScreen(
                modifier = modifier,
                message = state.error.toString(),
                retry = viewModel::initialize
            )
        }
    }
}