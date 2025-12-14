package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel
import com.orka.myfinances.lib.ui.models.ScreenTab
import com.orka.myfinances.ui.screens.history.ReceiveContent
import com.orka.myfinances.ui.screens.history.SaleContent
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun historyEntry(
    modifier: Modifier,
    destination: Destination.History
): NavEntry<Destination> = entry(destination) {
    val tabs = listOf(
        ScreenTab(
            index = 0,
            title = stringResource(R.string.sale),
            content = { modifier ->
                SaleContent(modifier, destination.saleViewModel as SaleContentViewModel)
            }
        ),
        ScreenTab(
            index = 1,
            title = stringResource(R.string.receive),
            content = { modifier ->
                ReceiveContent(modifier, destination.receiveViewModel as ReceiveContentViewModel)
            }
        )
    )

    MultipleTabScreen(
        modifier = modifier,
        title = stringResource(R.string.history),
        tabs = tabs
    )
}