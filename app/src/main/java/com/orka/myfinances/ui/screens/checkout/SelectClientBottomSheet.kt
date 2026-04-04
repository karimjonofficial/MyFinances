package com.orka.myfinances.ui.screens.checkout

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.components.SelectionBottomSheet
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectClientBottomSheet(
    modifier: Modifier = Modifier,
    state: State<ChunkMapState<ClientItemModel>>,
    selectedClient: ClientItemModel?,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onSelected: (ClientItemModel) -> Unit,
    onLoadMore: () -> Unit
) {
    SelectionBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        state = state,
        selectedItem = selectedClient,
        onSelected = onSelected,
        onLoadMore = onLoadMore
    )
}