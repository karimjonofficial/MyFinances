package com.orka.myfinances.ui.screens.order

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.application.LoggerImpl
import com.orka.myfinances.lib.ui.models.Text
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.ui.navigation.Navigator

@Composable
fun OrdersScreen(
    modifier: Modifier,
    viewModel: OrdersScreenViewModel,
    navigator: Navigator
) {
    LazyColumnScreen(
        modifier = modifier,
        title = stringResource(R.string.orders),
        item = { modifier, order ->

            OrderCard(
                modifier = modifier,
                order = order,
                onClick = { navigator.navigateToOrder(it) }
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
            repository = OrderRepository(),
            loading = Text.Res(R.string.loading),
            failure = Text.Res(R.string.failure),
            logger = LoggerImpl()
        )
    }
    viewModel.initialize()

    OrdersScreen(
        modifier = Modifier,
        viewModel = viewModel,
        navigator = DummyNavigator()
    )
}