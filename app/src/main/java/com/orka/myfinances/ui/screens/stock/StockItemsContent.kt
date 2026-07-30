package com.orka.myfinances.ui.screens.stock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.stockItems
import com.orka.myfinances.lib.ui.contents.LazyVerticalGridContentWithStickyHeader
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.cards.StockItemCard
import com.orka.myfinances.ui.map.toMap

@Composable
fun StockItemsContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    interactor: StockContentInteractor,
    state: State<ChunkUiModel<StockItemUiModel>>
) {
    LazyVerticalGridContentWithStickyHeader(
        modifier = modifier,
        contentPadding = contentPadding,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        item = { item ->
            StockItemCard(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(0.6f),
                item = item.model,
                onIncrease = { interactor.addToBasket(item.id) },
                onDecrease = { interactor.removeFromBasket(item.id) }
            )
        }
    )
}

@Preview(device = "id:pixel_10_pro_xl")
@Composable
private fun StockItemsContentPreview() {
    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = stringResource(R.string.warehouse)
    ) { paddingValues ->
        StockItemsContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp),
            interactor = StockContentInteractor.dummy,
            state = State.Success(
                value = ChunkUiModel(
                    size = 0,
                    pageIndex = 1,
                    nextPageIndex = null,
                    previousPageIndex = null,
                    content = stockItems.toMap()
                )
            )
        )
    }
}
