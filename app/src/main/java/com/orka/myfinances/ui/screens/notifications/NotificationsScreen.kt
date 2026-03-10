package com.orka.myfinances.ui.screens.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.application.viewmodels.notifications.NotificationsScreenViewModel
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun NotificationsScreen(
    modifier: Modifier,
    state: State,
    viewModel: NotificationsScreenViewModel
) {
    LazyColumnScreen(
        modifier = modifier,
        title = stringResource(R.string.notifications),
        viewModel = viewModel,
        state = state,
        item = { modifier, notification ->
            NotificationCard(
                modifier = modifier,
                notification = notification,
                onClick = { viewModel.read(notification) }
            )
        }
    )
}