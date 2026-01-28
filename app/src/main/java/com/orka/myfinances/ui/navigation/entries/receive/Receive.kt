package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.receive.ReceiveScreen

fun receiveEntry(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    destination: Destination.Receive
): NavEntry<Destination> = entry(destination) {

    ReceiveScreen(
        modifier = modifier,
        navigator = navigator,
        receive = destination.receive
    )
}