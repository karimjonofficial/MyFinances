package com.orka.myfinances.ui.screens.sale.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.cards.SaleCard
import com.orka.myfinances.ui.models.ui.SaleUiModel

@Composable
fun SaleContent(
    modifier: Modifier = Modifier,
    interactor: SaleContentInteractor,
    state: State<ChunkUiModel<SaleUiModel>>,
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
        item = { sale ->
            SaleCard(
                sale = sale.model,
                onClick = { interactor.select(sale) }
            )
        }
    )
}
