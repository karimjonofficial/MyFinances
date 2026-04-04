package com.orka.myfinances.ui.screens.notifications

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.notifications
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun NotificationsScreen(
    modifier: Modifier,
    state: State<ChunkMapState<NotificationUiModel>>,
    interactor: NotificationsScreenInteractor
) {
    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        title = stringResource(R.string.notifications),
        contentPadding = PaddingValues(horizontal = 4.dp),
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        state = state,
        item = { notification ->
            NotificationCard(
                modifier = modifier,
                notification = notification.model,
                onClick = { interactor.read(notification) }
            )
        }
    )
}

@Preview
@Composable
private fun NotificationsScreenPreview() {
    MyFinancesTheme {
        NotificationsScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(notifications.toChunkMapState()),
            interactor = NotificationsScreenInteractor.dummy
        )
    }
}