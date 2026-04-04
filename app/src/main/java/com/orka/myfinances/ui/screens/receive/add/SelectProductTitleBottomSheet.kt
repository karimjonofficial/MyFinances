package com.orka.myfinances.ui.screens.receive.add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.components.SelectionBottomSheet
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectProductTitleBottomSheet(
    modifier: Modifier = Modifier,
    state: State<ChunkMapState<ProductTitleItemModel>>,
    selectedProductTitle: ProductTitleItemModel?,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onSelected: (ProductTitleItemModel) -> Unit,
    onLoadMore: () -> Unit
) {
    SelectionBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        state = state,
        selectedItem = selectedProductTitle,
        onSelected = onSelected,
        onLoadMore = onLoadMore
    )
}
