package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.screens.ChunkMapState
import com.orka.myfinances.lib.ui.screens.LazyColumnContentWithStickyHeader
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.home.components.ProductTitleCard
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductTitleUiModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseProductTitlesInteractor

@Composable
fun ProductTitlesContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    viewModel: WarehouseProductTitlesInteractor,
    state: State<ChunkMapState<ProductTitleUiModel>>
) {
    LazyColumnContentWithStickyHeader(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state,
        viewModel = viewModel
    ) { _, item ->
        ProductTitleCard(
            productTitle = item.model,
            onClick = { viewModel.selectProduct(item.id) }
        )
    }
}
