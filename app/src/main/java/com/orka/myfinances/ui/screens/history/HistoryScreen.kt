package com.orka.myfinances.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.models.ScreenTab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    factory: Factory,
) {
    val tabs = listOf(
        ScreenTab(
            index = 0,
            title = stringResource(R.string.sale),
            content = { modifier ->
                SaleContent(
                    modifier = modifier,
                    viewModel = viewModel { factory.salesViewModel() }
                )
            }
        ),
        ScreenTab(
            index = 1,
            title = stringResource(R.string.receive),
            content = { modifier ->
                ReceiveContent(
                    modifier = modifier,
                    viewModel = viewModel { factory.receivesViewModel() }
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