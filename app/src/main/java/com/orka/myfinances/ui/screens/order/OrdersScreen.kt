package com.orka.myfinances.ui.screens.order

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.lib.LoggerImpl
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.data.repositories.order.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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
        viewModel = viewModel
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun OrderScreenPreview() {
    val viewModel = OrdersScreenViewModel(
        repository = OrderRepository(),
        loading = "Loading",
        failure = "Failure",
        logger = LoggerImpl(),
        coroutineScope = CoroutineScope(Dispatchers.Main)
    )
    viewModel.initialize()

    OrdersScreen(
        modifier = Modifier,
        viewModel = viewModel,
        navigator = DummyNavigator()
    )
}