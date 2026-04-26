package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.lib.ui.models.Tab
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.debt.history.DebtsHistoryContent
import com.orka.myfinances.ui.screens.history.HistoryScreen
import com.orka.myfinances.ui.screens.order.list.completed.OrdersHistoryContent
import com.orka.myfinances.ui.screens.receive.list.ReceiveContent
import com.orka.myfinances.ui.screens.sale.list.SaleContent

fun historyEntry(
    modifier: Modifier,
    destination: Destination.History,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val tabs = listOf(
        Tab(
            index = 0,
            title = stringResource(R.string.sale)
        ),
        Tab(
            index = 1,
            title = stringResource(R.string.receive)
        ),
        Tab(
            index = 2,
            title = stringResource(R.string.orders)
        ),
        Tab(
            index = 3,
            title = stringResource(R.string.debts)
        )
    )
    val saleViewModel = viewModel { factory.salesViewModel() }
    val receiveViewModel = viewModel { factory.receivesViewModel() }
    val ordersViewModel = viewModel { factory.ordersHistoryViewModel()}
    val debtsViewModel = viewModel { factory.debtHistoryViewModel()}

    val receiveState = receiveViewModel.uiState.collectAsState()
    val saleState = saleViewModel.uiState.collectAsState()
    val ordersState = ordersViewModel.uiState.collectAsState()
    val debtsState = debtsViewModel.uiState.collectAsState()

    HistoryScreen(
        modifier = modifier,
        tabs = tabs,
        tabContent = { index ->
            val contentModifier = Modifier.fillMaxSize()

            when (index) {
                0 -> {
                    SaleContent(
                        modifier = contentModifier,
                        state = saleState.value,
                        interactor = saleViewModel
                    )
                }

                1 -> {
                    ReceiveContent(
                        modifier = contentModifier,
                        state = receiveState.value,
                        interactor = receiveViewModel
                    )
                }

                2 -> {
                    OrdersHistoryContent(
                        modifier = contentModifier,
                        state = ordersState.value,
                        interactor = ordersViewModel
                    )
                }

                3 -> {
                    DebtsHistoryContent(
                        modifier = contentModifier,
                        interactor = debtsViewModel,
                        state = debtsState.value
                    )
                }
            }
        }
    )
}