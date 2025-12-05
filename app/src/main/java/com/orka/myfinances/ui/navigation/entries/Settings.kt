package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry

@OptIn(ExperimentalMaterial3Api::class)
fun settingsEntry(
    modifier: Modifier,
    destination: Destination.Settings,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings)) }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier.scaffoldPadding(paddingValues),
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
}