package com.orka.myfinances.lib.ui.contents

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State

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
            message = state.message.str()
        )

        is State.Failure -> FailureScreen(
            modifier = modifier,
            message = state.error.str(),
            retry = onRetry
        )

        is State.Success -> {
            val model = state.value
            content(modifier, model)
        }
    }
}