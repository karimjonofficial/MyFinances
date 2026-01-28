package com.orka.myfinances.ui.navigation.entries.order

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.lib.ui.screens.BlankScreen
import com.orka.myfinances.ui.navigation.Destination

fun orderEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Order
): NavEntry<Destination> = entry(destination) {
    BlankScreen(
        modifier = modifier,
        title = stringResource(R.string.orders)
    )
}