package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.notification.NotificationScreen
import com.orka.myfinances.ui.screens.notification.NotificationScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun notificationsEntry(
    modifier: Modifier,
    destination: Destination.Notifications
): NavEntry<Destination> = entry(destination) {

    NotificationScreen(
        modifier = modifier,
        viewModel = destination.viewModel as NotificationScreenViewModel
    )
}