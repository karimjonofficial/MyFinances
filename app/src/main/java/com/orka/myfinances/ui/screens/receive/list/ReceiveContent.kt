package com.orka.myfinances.ui.screens.receive.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.ui.screens.receive.list.components.ReceiveCard
import com.orka.myfinances.ui.screens.receive.list.viewmodel.ReceiveContentInteractor

@Composable
fun ReceiveContent(
    modifier: Modifier = Modifier,
    interactor: ReceiveContentInteractor
) {
    val state = interactor.uiState.collectAsState()

    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        arrangementSpace = 0.dp,
        state = state.value,
        refresh = interactor::initialize,
        loadMore = interactor::loadMore,
        item = { receive ->
            ReceiveCard(
                receive = receive.model,
                onClick = { interactor.select(receive) }
            )
        }
    )
}