package com.orka.myfinances.lib.ui.entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.screens.Screen
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

fun <T : Any, TLoading, TSuccess, TFailure> lazyColumnEntry(
    modifier: Modifier = Modifier,
    destination: T,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>,
    topBar: @Composable () -> Unit = {},
    item: @Composable (TSuccess) -> Unit,
): NavEntry<T> = NavEntry(key = destination) {

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