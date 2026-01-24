package com.orka.myfinances.ui.screens.catalog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.home.parts.FoldersList

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    state: CatalogScreenState,
    viewModel: CatalogScreenViewModel,
    navigator: Navigator
) {
    when (state) {
        is CatalogScreenState.Loading -> LoadingScreen(modifier)

        is CatalogScreenState.Failure -> FailureScreen(
            modifier = modifier,
            retry = { viewModel.initialize() }
        )

        is CatalogScreenState.Success -> FoldersList(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            items = state.folders,
            onFolderSelected = {
                when (it) {
                    is Catalog -> navigator.navigateToCatalog(it)
                    is Category -> navigator.navigateToCategory(it)
                }
            }
        )
    }
}