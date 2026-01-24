package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.factories.viewmodel.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.lib.ui.models.ScreenTab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.history.ReceiveContent
import com.orka.myfinances.ui.screens.history.SaleContent

@OptIn(ExperimentalMaterial3Api::class)
fun historyEntry(
    modifier: Modifier,
    destination: Destination.History,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val tabs = listOf(
        ScreenTab(
            index = 0,
            title = stringResource(R.string.sale),
            content = { modifier ->
                SaleContent(
                    modifier = modifier,
                    viewModel = viewModel { factory.saleViewModel() }
                )
            }
        ),
        ScreenTab(
            index = 1,
            title = stringResource(R.string.receive),
            content = { modifier ->
                ReceiveContent(
                    modifier = modifier,
                    viewModel = viewModel { factory.receiveViewModel() }
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