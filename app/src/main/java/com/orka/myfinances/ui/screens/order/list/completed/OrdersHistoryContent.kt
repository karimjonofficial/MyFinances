package com.orka.myfinances.ui.screens.order.list.completed

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.components.ListItem
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun OrdersHistoryContent(
    modifier: Modifier = Modifier,
    interactor: OrdersHistoryInteractor,
    state: State<ChunkMapState<HistoryOrderUiModel>>
) {
    val painter = painterResource(R.drawable.delivery_truck_speed)

    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        arrangementSpace = 8.dp,
        item = { item ->
            ListItem(
                painter = painter,
                headlineText = item.model.title,
                supportingText = item.model.count,
                price = item.model.price,
                dateTime = item.model.dateTime.str(),
                onClick = { interactor.select(item) }
            )
        }
    )
}