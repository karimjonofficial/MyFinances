package com.orka.myfinances.ui.screens.debt.history

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.debt.list.DebtCard
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel

@Composable
fun DebtsHistoryContent(
    modifier: Modifier = Modifier,
    interactor: DebtsHistoryContentInteractor,
    state: State<ChunkMapState<DebtUiModel>>
) {
    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        arrangementSpace = 8.dp,
        item = { item ->
            DebtCard(
                modifier = Modifier.padding(horizontal = 8.dp),
                debt = item.model,
                onClick = { interactor.select(item) }
            )
        }
    )
}