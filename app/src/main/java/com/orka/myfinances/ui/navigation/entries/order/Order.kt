package com.orka.myfinances.ui.navigation.entries.order

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination

fun orderEntry(destination: Destination.Order): NavEntry<Destination> = entry(destination) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.orders))
    }
}