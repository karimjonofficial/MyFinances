package com.orka.myfinances.ui.screens.product.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun ProductTitlesContent(
    modifier: Modifier = Modifier,
    interactor: ProductTitlesContentInteractor,
    state: State<ChunkMapState<ProductTitleUiModel>>
) {
    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore
    ) { item ->
        ProductTitleCard(
            productTitle = item.model,
            onClick = { interactor.selectProduct(item.id) }
        )
    }
}
