package com.orka.myfinances.lib.ui.contents

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.components.LazyColumn
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun <T> LazyColumnContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    arrangementSpace: Dp = 0.dp,
    state: State<List<T>>,
    refresh: () -> Unit,
    item: @Composable (item: T) -> Unit
) {
    when (state) {
        is State.Loading -> LoadingScreen(
            modifier = modifier,
            message = state.message.str()
        )

        is State.Success -> {
            val items = state.value
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
            retry = refresh
        )
    }
}