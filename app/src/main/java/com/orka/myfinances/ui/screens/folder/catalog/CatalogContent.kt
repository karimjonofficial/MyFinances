package com.orka.myfinances.ui.screens.folder.catalog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CatalogContent(
    modifier: Modifier = Modifier,
    model: CatalogScreenModel,
    interactor: CatalogScreenInteractor
) {
    FoldersList(
        modifier = modifier,
        items = model.folders,
        contentPadding = PaddingValues(horizontal = 16.dp),
        onFolderSelected = { interactor.select(it) }
    )
}