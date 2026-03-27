package com.orka.myfinances.ui.screens.notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.fixtures.resources.models.notifications
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun NotificationsScreen(
    modifier: Modifier,
    state: State<List<Notification>>,
    interactor: NotificationsScreenInteractor
) {
    LazyColumnScreen(
        modifier = modifier,
        title = stringResource(R.string.notifications),
        refresh = interactor::refresh,
        state = state,
        item = { notification ->
            NotificationCard(
                modifier = modifier,
                notification = notification,
                onClick = { interactor.read(notification) }
            )
        }
    )
}

@Preview
@Composable
private fun NotificationsScreenPreview() {
    NotificationsScreen(
        modifier = Modifier.fillMaxSize(),
        state = State.Success(notifications),
        interactor = NotificationsScreenInteractor.dummy
    )
}