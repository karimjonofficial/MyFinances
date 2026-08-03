package com.orka.myfinances.ui.screens.receive.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.fixtures.resources.models.receive.receives
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.cards.ReceiveCard
import com.orka.myfinances.ui.models.ui.ReceiveUiModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun ReceiveContent(
    modifier: Modifier = Modifier,
    state: State<ChunkUiModel<ReceiveUiModel>>,
    interactor: ReceiveContentInteractor,
    searchActive: Boolean = false
) {
    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        arrangementSpace = 0.dp,
        state = state,
        refresh = interactor::refresh,
        loadMore = {
            if (searchActive) interactor.searchMore()
            else interactor.loadMore()
        },
        item = { receive ->
            ReceiveCard(
                receive = receive.model,
                onClick = { interactor.select(receive) }
            )
        }
    )
}

@Preview
@Composable
private fun ReceiveContentPreview() {
    MyFinancesTheme {
        ScaffoldPreview(
            modifier = Modifier.fillMaxSize(),
            title = "Receives",
        ) { paddingValues ->
            ReceiveContent(
                modifier = Modifier.scaffoldPadding(paddingValues),
                state = State.Success(value = receives.toChunkMapState()),
                interactor = ReceiveContentInteractor.dummy
            )
        }
    }
}
