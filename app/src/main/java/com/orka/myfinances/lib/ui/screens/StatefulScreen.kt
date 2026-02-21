package com.orka.myfinances.lib.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun <T> StatefulScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable (State) -> Unit = {},
    state: State,
    onRetry: () -> Unit = {},
    content: @Composable (Modifier, T) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = { bottomBar(state) },
    ) { paddingValues ->
        val m = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is State.Initial -> LoadingScreen(modifier = m)

            is State.Loading -> LoadingScreen(
                modifier = m,
                message = state.message.str()
            )

            is State.Failure -> FailureScreen(
                modifier = m,
                message = state.error.str(),
                retry = onRetry
            )

            is State.Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                val model = state.value as T
                content(m, model)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> StatefulScreen(
    modifier: Modifier = Modifier,
    title: String,
    bottomBar: @Composable (State) -> Unit = {},
    state: State,
    onRetry: () -> Unit = {},
    content: @Composable (Modifier, T) -> Unit
) {
    StatefulScreen(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = title) }) },
        bottomBar = bottomBar,
        state = state,
        onRetry = onRetry,
        content = content
    )
}