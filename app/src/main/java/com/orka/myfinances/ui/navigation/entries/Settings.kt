package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry

fun settingsEntry(
    modifier: Modifier,
    destination: Destination.Settings,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(modifier, destination) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { navigationManager.navigateToTemplates() }) {
                Text(text = stringResource(R.string.templates))
            }

            VerticalSpacer(8)
            Button(onClick = { navigationManager.navigateToClients() }) {
                Text(text = stringResource(R.string.clients))
            }
        }
    }
}