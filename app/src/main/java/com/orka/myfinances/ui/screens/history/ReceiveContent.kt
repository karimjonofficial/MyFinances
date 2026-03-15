package com.orka.myfinances.ui.screens.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.screens.LazyColumnContentWithStickyHeader
import com.orka.myfinances.ui.screens.history.components.ReceiveCard
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentInteractor

@Composable
fun ReceiveContent(
    modifier: Modifier = Modifier,
    interactor: ReceiveContentInteractor
) {
    val state = interactor.uiState.collectAsState()

    LazyColumnContentWithStickyHeader(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        arrangementSpace = 0.dp,
        state = state.value,
        viewModel = interactor,
        item = { modifier, receive ->
            ReceiveCard(
                modifier = modifier,
                receive = receive.model,
                onClick = { interactor.select(receive) }
            )
        }
    )
}