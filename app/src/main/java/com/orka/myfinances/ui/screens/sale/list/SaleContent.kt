package com.orka.myfinances.ui.screens.sale.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun SaleContent(
    modifier: Modifier = Modifier,
    interactor: SaleContentInteractor,
    state: State<ChunkUiModel<SaleUiModel>>
) {
    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
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