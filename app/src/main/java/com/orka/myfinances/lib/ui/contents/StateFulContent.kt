package com.orka.myfinances.lib.ui.contents

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.statuses.failure.str
import com.orka.myfinances.ui.statuses.loading.str

@Composable
fun <T> StateFulContent(
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    state: State<T>,
    content: @Composable (Modifier, T) -> Unit
) {
    when (state) {
        is State.Loading -> LoadingScreen(
            modifier = modifier,
            message = state.status.str()
        )

        is State.Failure -> FailureScreen(
            modifier = modifier,
            retry = onRetry,
            message = state.status.str()
        )

        is State.Success -> {
            val model = state.value
            content(modifier, model)
        }
    }
}