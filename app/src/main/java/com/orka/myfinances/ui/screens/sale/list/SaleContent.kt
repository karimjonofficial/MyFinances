package com.orka.myfinances.ui.screens.sale.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun SaleContent(
    modifier: Modifier = Modifier,
    interactor: SaleContentInteractor,
    state: State<ChunkMapState<SaleUiModel>>
) {
    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        arrangementSpace = 0.dp,
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        item = { sale ->
            SaleCard(
                sale = sale.model,
                onClick = { interactor.select(sale) }
            )
        }
    )
}