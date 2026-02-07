package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.history.HistoryScreen

@OptIn(ExperimentalMaterial3Api::class)
fun historyEntry(
    modifier: Modifier,
    destination: Destination.History,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    HistoryScreen(
        modifier = modifier,
        factory = factory,
        navigator = navigator
    )
}