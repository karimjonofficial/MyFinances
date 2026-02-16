package com.orka.myfinances.ui.screens.order

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.application.LoggerImpl
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen

@Composable
fun OrdersScreen(
    modifier: Modifier,
    viewModel: OrdersScreenViewModel
) {
    LazyColumnScreen(
        modifier = modifier,
        title = stringResource(R.string.orders),
        item = { modifier, order ->

            OrderCard(
                modifier = modifier,
                order = order.model,
                onClick = { viewModel.select(order.order) }
            )
        },
        arrangementSpace = 4.dp,
        viewModel = viewModel
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun OrderScreenPreview() {
    val viewModel = viewModel {
        OrdersScreenViewModel(
            repository = OrderRepository(generator = { id1 }),
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            navigator = DummyNavigator(),
            logger = LoggerImpl()
        )
    }
    viewModel.initialize()

    OrdersScreen(
        modifier = Modifier,
        viewModel = viewModel
    )
}