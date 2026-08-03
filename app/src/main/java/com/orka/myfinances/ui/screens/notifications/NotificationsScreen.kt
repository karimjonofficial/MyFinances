package com.orka.myfinances.ui.screens.notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.notifications
import com.orka.myfinances.lib.ui.components.SearchTopAppBar
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.cards.NotificationCard
import com.orka.myfinances.ui.models.ui.NotificationUiModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun NotificationsScreen(
    modifier: Modifier,
    state: State<ChunkUiModel<NotificationUiModel>>,
    interactor: NotificationsScreenInteractor
) {
    val searchMode = rememberSaveable { mutableStateOf(false) }
    val searchText = rememberSaveable { mutableStateOf("") }

    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        topBar = {
            SearchTopAppBar(
                title = stringResource(R.string.notifications),
                onSearch = interactor::search,
                searchMode = searchMode.value,
                onSearchModeChange = { searchMode.value = it },
                searchText = searchText.value,
                onSearchTextChange = { searchText.value = it }
            )
        },
        refresh = interactor::refresh,
        loadMore = {
            if (searchMode.value) interactor.searchMore()
            else interactor.loadMore()
        },
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
