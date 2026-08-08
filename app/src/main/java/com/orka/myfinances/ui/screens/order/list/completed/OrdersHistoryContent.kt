package com.orka.myfinances.ui.screens.order.list.completed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.format.LocalFormatter
import com.orka.myfinances.lib.ui.components.ListItem
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.ui.HistoryOrderUiModel

@Composable
fun OrdersHistoryContent(
    modifier: Modifier = Modifier,
    interactor: OrdersHistoryInteractor,
    state: State<ChunkUiModel<HistoryOrderUiModel>>,
    searchActive: Boolean = false
) {
    val painter = painterResource(R.drawable.delivery_truck_speed)
    val formatter = LocalFormatter.current

    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        state = state,
        refresh = interactor::refresh,
        contentPadding = PaddingValues(horizontal = 8.dp),
        arrangementSpace = 2.dp,
        loadMore = {
            if (searchActive) interactor.searchMore()
            else interactor.loadMore()
        },
        item = { item ->
            ListItem(
                painter = painter,
                headlineText = item.model.title,
                supportingText = stringResource(R.string.items_f, formatter.formatNumber(item.model.size)),
                price = stringResource(R.string.uzs_f, formatter.formatNumber(item.model.price)),
                dateTime = formatter.formatDateTime(item.model.dateTime),
                onClick = { interactor.select(item) }
            )
        }
    )
}
