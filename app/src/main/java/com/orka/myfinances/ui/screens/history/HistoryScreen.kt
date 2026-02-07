package com.orka.myfinances.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.models.ScreenTab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen
import com.orka.myfinances.ui.navigation.Navigator

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    factory: Factory,
    navigator: Navigator
) {
    val tabs = listOf(
        ScreenTab(
            index = 0,
            title = stringResource(R.string.sale),
            content = { modifier ->
                SaleContent(
                    modifier = modifier,
                    viewModel = viewModel { factory.saleViewModel() },
                    navigator = navigator
                )
            }
        ),
        ScreenTab(
            index = 1,
            title = stringResource(R.string.receive),
            content = { modifier ->
                ReceiveContent(
                    modifier = modifier,
                    viewModel = viewModel { factory.receiveViewModel() },
                    navigator = navigator
                )
            }
        )
    )

    MultipleTabScreen(
        modifier = modifier,
        title = stringResource(R.string.history),
        tabs = tabs
    )
}