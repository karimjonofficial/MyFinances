package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.viewmodel.list.State
import com.orka.myfinances.lib.viewmodel.list.ListViewModel

@Composable
fun <T> LazyColumnContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 0.dp,
    state: State,
    viewModel: ListViewModel<T>,
    item: @Composable (modifier: Modifier, item: T) -> Unit
) {
    when (state) {
        is State.Initial -> LoadingScreen(modifier)

        is State.Loading -> LoadingScreen(
            modifier = modifier,
            message = state.message.str()
        )

        is State.Success<*> -> {
            @Suppress("UNCHECKED_CAST")
            val items = state.value as List<T>
            LazyColumn(
                modifier = modifier,
                contentPadding = contentPadding,
                arrangementSpace = arrangementSpace,
                items = items,
                item = item
            )
        }

        is State.Failure -> FailureScreen(
            modifier = modifier,
            message = state.error.str(),
            retry = viewModel::initialize
        )
    }
}
