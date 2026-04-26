package com.orka.myfinances.ui.screens.order.list.completed

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.order.list.OrderCard
import com.orka.myfinances.ui.screens.order.list.OrderUiModel
import com.orka.myfinances.ui.screens.order.list.OrdersScreenInteractor

@Composable
fun OrdersHistoryContent(
    modifier: Modifier = Modifier,
    interactor: OrdersScreenInteractor,
    state: State<ChunkMapState<OrderUiModel>>
) {
    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        arrangementSpace = 8.dp,
        item = { item ->
            OrderCard(
                modifier = Modifier.padding(horizontal = 8.dp),
                order = item.model,
                onClick = { interactor.select(item) }
            )
        }
    )
}