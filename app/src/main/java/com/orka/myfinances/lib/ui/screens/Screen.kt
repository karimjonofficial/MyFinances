package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun <TLoading, TSuccess, TFailure> Screen(
    modifier: Modifier = Modifier,
    state: State<TLoading, List<TSuccess>, TFailure>,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>,
    item: @Composable (TSuccess) -> Unit
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

            LazyColumn(modifier = modifier) {
                items(items = state.value) {
                    item(it)
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