package com.orka.myfinances.ui.navigation.entries.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.components.SelectionBottomSheet
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.folder.models.TemplateItemModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectTemplateBottomSheet(
    modifier: Modifier = Modifier,
    state: State<ChunkMapState<TemplateItemModel>>,
    selectedItem: TemplateItemModel?,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onSelected: (TemplateItemModel) -> Unit,
    onLoadMore: () -> Unit
) {
    SelectionBottomSheet(
        modifier = modifier,
        state = state,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        onSelected = onSelected,
        onLoadMore = onLoadMore,
        selectedItem = selectedItem
    )
}
