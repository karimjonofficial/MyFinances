package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.history.HistoryScreen
import com.orka.myfinances.ui.screens.receive.list.ReceiveContent
import com.orka.myfinances.ui.screens.sale.list.SaleContent

fun historyEntry(
    modifier: Modifier,
    destination: Destination.History,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val saleViewModel = viewModel { factory.salesViewModel() }
    val receiveViewModel = viewModel { factory.receivesViewModel() }

    val receiveState = receiveViewModel.uiState.collectAsState()
    val saleState = saleViewModel.uiState.collectAsState()

    HistoryScreen(
        modifier = modifier,
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
            }
        }
    )
}