package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.lib.ui.screens.BlankScreen
import com.orka.myfinances.ui.managers.navigation.Destination

fun searchEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Search
): NavEntry<Destination> = entry(destination) {
    BlankScreen(
        modifier = modifier,
        title = stringResource(R.string.search)
    )
}