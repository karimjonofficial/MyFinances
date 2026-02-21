package com.orka.myfinances.ui.screens.order

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
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.order.components.OrderCard
import com.orka.myfinances.ui.screens.order.viewmodel.OrdersScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    state: State,
    viewModel: OrdersScreenViewModel
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
        viewModel = viewModel,
        item = { modifier, item ->
            OrderCard(
                modifier = modifier.padding(horizontal = 8.dp),
                order = item.model,
                onClick = { viewModel.select(item.order) }
            )
        }
    )
}
