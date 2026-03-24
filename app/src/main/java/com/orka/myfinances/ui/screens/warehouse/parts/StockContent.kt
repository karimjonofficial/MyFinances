package com.orka.myfinances.ui.screens.warehouse.parts

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
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.ChunkMapState
import com.orka.myfinances.lib.ui.screens.LazyVerticalGridContentWithStickyHeader
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCard
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockItemUiModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockContentInteractor
import kotlinx.coroutines.flow.MutableStateFlow

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
        viewModel = interactor,
        item = { modifier, item ->
            StockItemCard(
                modifier = modifier,
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
            interactor = object : StockContentInteractor {
                override val uiState = MutableStateFlow(State.Loading<ChunkMapState<StockItemUiModel>>(UiText.Str("loading")))
                override val title = MutableStateFlow(category1.name)
                override fun addToBasket(id: Id) {}
                override fun addProduct() {}
                override fun receive() {}
                override fun initialize() {}
                override fun loadMore() {}
            },
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
