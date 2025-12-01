package com.orka.myfinances.lib.ui.entry

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.screens.Screen
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

fun <T : Any, TLoading, TSuccess, TFailure> lazyColumnEntry(
    modifier: Modifier = Modifier,
    destination: T,
    viewModel: ListViewModel<TLoading, TSuccess, TFailure>,
    item: @Composable (TSuccess) -> Unit
): NavEntry<T> {
    return NavEntry(key = destination) {
        val state = viewModel.uiState.collectAsState()

        Surface(modifier) {
            Screen(
                state = state.value,
                viewModel = viewModel,
                item = item
            )
        }
    }
}