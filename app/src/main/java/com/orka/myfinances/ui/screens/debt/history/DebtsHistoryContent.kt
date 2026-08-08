package com.orka.myfinances.ui.screens.debt.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.cards.DebtCard
import com.orka.myfinances.ui.models.ui.DebtUiModel

@Composable
fun DebtsHistoryContent(
    modifier: Modifier = Modifier,
    interactor: DebtsHistoryContentInteractor,
    state: State<ChunkUiModel<DebtUiModel>>,
    searchActive: Boolean = false
) {
    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        state = state,
        refresh = interactor::refresh,
        contentPadding = PaddingValues(horizontal = 8.dp),
        arrangementSpace = 2.dp,
        loadMore = {
            if (searchActive) interactor.searchMore()
            else interactor.loadMore()
        },
        item = { item ->
            DebtCard(
                modifier = Modifier.padding(horizontal = 8.dp),
                debt = item.model,
                onClick = { interactor.select(item) }
            )
        }
    )
}
