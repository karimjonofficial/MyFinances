package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreen

fun addReceiveEntry(
    modifier: Modifier,
    destination: Destination.AddStockItem,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = "${destination.category.id}") {
        factory.addReceiveViewModel(destination.category)
    }
    val state = viewModel.state.collectAsState()

    AddReceiveScreen(
        modifier = modifier,
        viewModel = viewModel,
        state = state.value,
        category = destination.category,
        navigator = navigator
    )
}