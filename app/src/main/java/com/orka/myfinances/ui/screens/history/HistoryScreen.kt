package com.orka.myfinances.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.ScreenTab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen
import com.orka.myfinances.application.viewmodels.history.ReceiveContentViewModel
import com.orka.myfinances.application.viewmodels.history.SaleContentViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    saleViewModel: SaleContentViewModel,
    receiveViewModel: ReceiveContentViewModel,
) {
    val tabs = listOf(
        ScreenTab(
            index = 0,
            title = stringResource(R.string.sale),
            content = { modifier ->
                SaleContent(
                    modifier = modifier,
                    viewModel = saleViewModel
                )
            }
        ),
        ScreenTab(
            index = 1,
            title = stringResource(R.string.receive),
            content = { modifier ->
                ReceiveContent(
                    modifier = modifier,
                    viewModel = receiveViewModel
                )
            }
        )
    )

    MultipleTabScreen(
        modifier = modifier,
        title = stringResource(R.string.history),
        tabs = tabs,
        onBack = saleViewModel::back
    )
}