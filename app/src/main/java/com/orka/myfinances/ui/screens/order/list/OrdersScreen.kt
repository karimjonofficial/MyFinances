package com.orka.myfinances.ui.screens.order.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.format.FormatDateImpl
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.resources.models.order.orders
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    state: State<ChunkMapState<OrderUiModel>>,
    interactor: OrdersScreenInteractor
) {
    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.orders)) },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            )
        },
        arrangementSpace = 8.dp,
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        item = { item ->
            OrderCard(
                modifier = Modifier.padding(horizontal = 8.dp),
                order = item.model,
                onClick = { interactor.select(item) }
            )
        }
    )
}

@Preview
@Composable
private fun OrdersScreenPreview() {
    MyFinancesTheme {
        OrdersScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(
                value = orders.toChunkMapState(
                    FormatPriceImpl(),
                    FormatDecimalImpl(),
                    FormatDateImpl()
                )
            ),
            interactor = OrdersScreenInteractor.dummy
        )
    }
}
