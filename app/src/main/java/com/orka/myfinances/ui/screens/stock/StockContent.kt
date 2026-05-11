package com.orka.myfinances.ui.screens.stock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.resources.models.stockItems
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.contents.LazyVerticalGridContentWithStickyHeader
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun StockContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    interactor: StockContentInteractor,
    state: State<ChunkMapState<StockItemUiModel>>
) {
    LazyVerticalGridContentWithStickyHeader(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        item = { item ->
            StockItemCard(
                item = item.model,
                onIncrease = { interactor.addToBasket(item.id) },
                onDecrease = { interactor.removeFromBasket(item.id) }
            )
        }
    )
}

@Preview
@Composable
private fun StockContentPreview() {
    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = stringResource(R.string.warehouse)
    ) { paddingValues ->
        StockContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp),
            interactor = StockContentInteractor.dummy,
            state = State.Success(
                value = ChunkMapState(
                    count = 0,
                    pageIndex = 1,
                    nextPageIndex = null,
                    previousPageIndex = null,
                    content = stockItems.toMap(FormatPriceImpl(), FormatDecimalImpl())
                )
            )
        )
    }
}
