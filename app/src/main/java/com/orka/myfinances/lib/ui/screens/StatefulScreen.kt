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
import com.orka.myfinances.lib.ui.viewmodel.extensions.isInitial

@Composable
fun <T> StatefulScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable (State<T>) -> Unit = {},
    bottomBar: @Composable (State<T>) -> Unit = {},
    state: State<T>,
    onInitialize: () -> Unit = {},
    onRetry: () -> Unit = {},
    content: @Composable (Modifier, T) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { topBar(state) },
        bottomBar = { bottomBar(state) },
    ) { paddingValues ->
        val modifier = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is State.Loading -> LoadingScreen(
                modifier = modifier,
                message = state.message.str(),
                action = if(state.isInitial()) onInitialize else null
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> StatefulScreen(
    modifier: Modifier = Modifier,
    title: String,
    bottomBar: @Composable (State<T>) -> Unit = {},
    state: State<T>,
    onInitialize: () -> Unit = {},
    onRetry: () -> Unit = {},
    content: @Composable (Modifier, T) -> Unit
) {
    StatefulScreen(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = title) }) },
        bottomBar = bottomBar,
        state = state,
        onInitialize = onInitialize,
        onRetry = onRetry,
        content = content
    )
}
