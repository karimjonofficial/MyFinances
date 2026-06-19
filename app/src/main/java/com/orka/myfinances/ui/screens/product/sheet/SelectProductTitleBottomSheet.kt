package com.orka.myfinances.ui.screens.product.sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.components.SelectionBottomSheet
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.models.ProductTitleItemModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectProductTitleBottomSheet(
    modifier: Modifier = Modifier,
    state: State<ChunkUiModel<ProductTitleItemModel>>,
    selectedProductTitle: ProductTitleItemModel?,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onSelected: (ProductTitleItemModel) -> Unit,
    onLoadMore: () -> Unit,
    onSearch: (String) -> Unit
) {
    SelectionBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        state = state,
        selectedItem = selectedProductTitle,
        onSelected = onSelected,
        onLoadMore = onLoadMore,
        onSearch = onSearch
    )
}
