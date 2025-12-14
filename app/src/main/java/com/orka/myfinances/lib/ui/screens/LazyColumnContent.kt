package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun <TLoading, TSuccess, TFailure> LazyColumnContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 4.dp,
    state: State<TLoading, List<TSuccess>, TFailure>,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>,
    item: @Composable (modifier: Modifier, item: TSuccess) -> Unit
) {
    when (state) {
        is State.Initial -> LoadingScreen(modifier)

        is State.Loading -> LoadingScreen(
            modifier = modifier,
            message = state.message.toString()
        )

        is State.Success -> LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            arrangementSpace = arrangementSpace,
            items = state.value,
            item = item
        )

        is State.Failure -> FailureScreen(
            modifier = modifier,
            message = state.error.toString(),
            retry = viewModel::initialize
        )
    }
}