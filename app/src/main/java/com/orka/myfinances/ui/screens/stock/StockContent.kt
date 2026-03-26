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
import com.orka.myfinances.data.models.Id
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
    state: State<ChunkMapState<StockItemUiModel>>,
    onStockItemClick: (Id) -> Unit,
) {
    LazyVerticalGridContentWithStickyHeader(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = state,
        refresh = interactor::initialize,
        loadMore = interactor::loadMore,
        item = { item ->
            StockItemCard(
                item = item.model,
                onClick = { onStockItemClick(item.id) }
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
            contentPadding = PaddingValues(),
            interactor = StockContentInteractor.dummy,
            state = State.Success(
                value = ChunkMapState(
                    count = 0,
                    pageIndex = 1,
                    nextPageIndex = null,
                    previousPageIndex = null,
                    content = emptyMap()
                )
            ),
            onStockItemClick = {}
        )
    }
}
