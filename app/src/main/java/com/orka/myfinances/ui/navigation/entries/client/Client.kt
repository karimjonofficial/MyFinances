package com.orka.myfinances.ui.navigation.entries.client

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.client.ClientScreen

fun clientEntry(modifier: Modifier, destination: Destination.Client): NavEntry<Destination> =
    entry(destination) {
        ClientScreen(modifier, destination.client)
    }