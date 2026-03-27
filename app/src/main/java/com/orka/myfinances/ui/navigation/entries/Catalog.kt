package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreen
import com.orka.myfinances.ui.screens.folder.home.parts.AddFolderDialog

fun catalogEntry(
    modifier: Modifier,
    destination: Destination.Catalog,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "${destination.id.value}",
        initializer = { factory.catalogViewModel(destination.id) }
    )
    val uiState = viewModel.uiState.collectAsState()
    val dialogState = viewModel.dialogState.collectAsState()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    CatalogScreen(
        modifier = modifier,
        state = uiState.value,
        dialogVisible = dialogVisible.value,
        onAddFolder = { dialogVisible.value = true },
        interactor = viewModel
    ) {
        AddFolderDialog(
            state = dialogState.value,
            dismissRequest = { dialogVisible.value = false },
            onAddTemplateClick = { viewModel.navigateToAddTemplate() },
            onSuccess = { name, type, templateId -> viewModel.addFolder(name, type, templateId) },
            onCancel = { dialogVisible.value = false }
        )
    }
}
