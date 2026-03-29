package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.orka.myfinances.ui.navigation.entries.home.SelectTemplateBottomSheet
import com.orka.myfinances.ui.screens.folder.models.TemplateItemModel
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreen
import com.orka.myfinances.ui.screens.folder.components.AddFolderDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun catalogEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "screen_${destination.id.value}",
        initializer = { factory.catalogViewModel(destination.id) }
    )
    val sheetViewModel = viewModel(
        key = "sheet_${destination.id.value}",
        initializer = { factory.templateBottomSheetViewModel() }
    )
    val uiState = viewModel.uiState.collectAsState()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val templatesState = sheetViewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    val template = retain { mutableStateOf<TemplateItemModel?>(null) }
    val coroutineScope = rememberCoroutineScope()

    CatalogScreen(
        modifier = modifier,
        state = uiState.value,
        dialogVisible = dialogVisible.value,
        onAddFolder = { dialogVisible.value = true },
        interactor = viewModel,
        dialog = {
            AddFolderDialog(
                template = template.value,
                dismissRequest = { dialogVisible.value = false },
                onUnfoldTemplates = { sheetVisible.value = true },
                onSuccess = { name, type, templateId ->
                    viewModel.addFolder(
                        name,
                        type,
                        templateId
                    )
                },
                onCancel = { dialogVisible.value = false }
            )
        },
        bottomSheet = {
            LaunchedEffect(Unit) {
                sheetViewModel.initialize()
            }
            SelectTemplateBottomSheet(
                state = templatesState.value,
                onDismissRequest = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            sheetVisible.value = false
                        }
                    }
                },
                onSelected = {
                    template.value = it
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            sheetVisible.value = false
                        }
                    }
                },
                sheetState = sheetState,
                onLoadMore = sheetViewModel::loadMore
            )
        },
        sheetVisible = sheetVisible.value
    )
}
