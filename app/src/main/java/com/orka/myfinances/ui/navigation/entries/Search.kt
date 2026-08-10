package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.search.SearchScreen

fun searchEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Search
): NavEntry<Destination> = entry(destination) {
    SearchScreen(modifier)
}