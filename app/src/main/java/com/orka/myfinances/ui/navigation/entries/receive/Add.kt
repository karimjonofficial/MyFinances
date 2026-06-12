package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreen
import com.orka.myfinances.ui.models.ProductTitleItemModel
import com.orka.myfinances.ui.screens.product.sheet.SelectProductTitleBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun addReceiveEntry(
    modifier: Modifier,
    destination: Destination.AddStockItem,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val viewModel = viewModel(
        key = "${destination.id}",
        initializer = { factory.addReceiveViewModel(destination.id) }
    )
    val sheetViewModel = viewModel(
        key = "product-title-sheet-${destination.id}",
        initializer = { factory.productTitleBottomSheetViewModel(destination.id) }
    )
    val state = viewModel.uiState.collectAsState()
    val title = retain { mutableStateOf<ProductTitleItemModel?>(null) }

    AddReceiveScreen(
        modifier = modifier,
        product = title.value,
        interactor = viewModel,
        state = state.value,
        onSelectProductClick = {
            sheetViewModel.refresh()
            sheetVisible.value = true
        }
    )

    if (sheetVisible.value) {
        val productState = sheetViewModel.uiState.collectAsState()

        LaunchedEffect(destination.id) {
            sheetViewModel.initialize()
        }

        SelectProductTitleBottomSheet(
            state = productState.value,
            selectedProductTitle = title.value,
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            onSelected = {
                title.value = it
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            onLoadMore = sheetViewModel::loadMore
        )
    }
}
