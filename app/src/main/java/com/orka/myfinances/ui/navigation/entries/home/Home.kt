package com.orka.myfinances.ui.navigation.entries.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.components.dialogs.AddProductDialog
import com.orka.myfinances.ui.models.item.TemplateItemModel
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.basket.BasketContent
import com.orka.myfinances.ui.components.dialogs.AddFolderDialog
import com.orka.myfinances.ui.screens.folder.home.FoldersContent
import com.orka.myfinances.ui.screens.folder.home.parts.StockItemsRow
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.profile.ProfileContent
import com.orka.myfinances.ui.screens.templates.sheet.SelectTemplateBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val branchId = session.branchId.value.toString()
    val addFolderDialogVisible = rememberSaveable { mutableStateOf(false) }
    val addProductDialogVisible = rememberSaveable { mutableStateOf(false) }

    val foldersViewModel = viewModel(
        key = "folders_$branchId",
        initializer = { factory.foldersViewModel() }
    )
    val basketViewModel = viewModel(
        key = "basket_$branchId",
        initializer = { factory.basketViewModel() }
    )
    val profileViewModel = viewModel(
        key = "profile_$branchId",
        initializer = { factory.profileViewModel() }
    )

    val profileScrollState = rememberLazyListState()

    HomeScreen(
        modifier = modifier,
        topBar = {
            HomeScreenTopBar(
                index = it,
                onAddFolder = { addFolderDialogVisible.value = true },
                foldersViewModel = foldersViewModel,
                basketViewModel = basketViewModel,
                profileViewModel = profileViewModel,
                profileScrollState = profileScrollState
            )
        },
        content = { contentModifier, index ->
            val sheetVisible = rememberSaveable { mutableStateOf(false) }

            when (index) {
                0 -> {
                    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)
                    val coroutineScope = rememberCoroutineScope()
                    val foldersState = foldersViewModel.uiState.collectAsState()
                    val template = retain { mutableStateOf<TemplateItemModel?>(null) }

                    FoldersContent(
                        modifier = contentModifier,
                        state = foldersState.value,
                        interactor = foldersViewModel,
                        onAddProductClick = { addProductDialogVisible.value = true }
                    ) { ids ->
                        ids.forEach {
                            item {
                                val viewModel = viewModel(
                                    key = "stock_${it.value}_${branchId}",
                                    initializer = { factory.stockItemsViewModel(it) }
                                )
                                val state = viewModel.uiState.collectAsState()

                                VerticalSpacer(16)
                                StockItemsRow(
                                    title = stringResource(R.string.pinned_category),
                                    state = state.value,
                                    interactor = viewModel
                                )
                            }
                        }
                    }

                    if (addFolderDialogVisible.value) {
                        AddFolderDialog(
                            dismissRequest = { addFolderDialogVisible.value = false },
                            onUnfoldTemplates = { sheetVisible.value = true },
                            onSuccess = foldersViewModel::addFolder,
                            template = template.value
                        )
                    }

                    if(addProductDialogVisible.value) {
                        AddProductDialog(
                            onDismissRequest = { addProductDialogVisible.value = false },
                            onSuccess = foldersViewModel::addProduct
                        )
                    }

                    if (sheetVisible.value) {
                        val sheetViewModel = viewModel(
                            key = "sheet_$branchId",
                            initializer = { factory.templateBottomSheetViewModel() }
                        )
                        val state = sheetViewModel.uiState.collectAsState()

                        SelectTemplateBottomSheet(
                            onDismissRequest = {
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        sheetVisible.value = false
                                    }
                                }
                            },
                            sheetState = sheetState,
                            state = state.value,
                            onSelected = {
                                template.value = it
                                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        sheetVisible.value = false
                                    }
                                }
                            },
                            selectedItem = template.value,
                            onLoadMore = sheetViewModel::loadMore,
                            onSearch = sheetViewModel::search
                        )
                    }

                }

                1 -> {
                    val basketState = basketViewModel.uiState.collectAsState()

                    BasketContent(
                        modifier = contentModifier,
                        state = basketState.value,
                        interactor = basketViewModel
                    )
                }

                2 -> {
                    val profileState = profileViewModel.uiState.collectAsState()

                    ProfileContent(
                        modifier = contentModifier,
                        state = profileState.value,
                        scrollState = profileScrollState,
                        interactor = profileViewModel
                    )
                }
            }
        }
    )
}
