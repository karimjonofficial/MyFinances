package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.notification.NotificationScreen

@OptIn(ExperimentalMaterial3Api::class)
fun notificationsEntry(
    modifier: Modifier,
    destination: Destination.Notifications,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.notificationsViewModel() }
    val state = viewModel.uiState.collectAsState()

    NotificationScreen(
        modifier = modifier,
        state = state.value,
        viewModel = viewModel
    )
}