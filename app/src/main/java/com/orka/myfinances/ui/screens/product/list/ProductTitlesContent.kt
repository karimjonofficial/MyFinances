package com.orka.myfinances.ui.screens.product.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.cards.ProductTitleCard
import com.orka.myfinances.ui.models.ui.ProductTitleUiModel

@Composable
fun ProductTitlesContent(
    modifier: Modifier = Modifier,
    interactor: ProductTitlesContentInteractor,
    state: State<ChunkUiModel<ProductTitleUiModel>>,
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
        }
    ) { item ->
        ProductTitleCard(
            productTitle = item.model,
            onClick = { interactor.selectProduct(item.id) }
        )
    }
}
