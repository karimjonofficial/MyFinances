package com.orka.myfinances.ui.screens.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.ui.managers.Navigator

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    catalog: Catalog,
    state: CatalogScreenState,
    viewModel: CatalogScreenViewModel,
    navigator: Navigator
) {
    Scaffold(
        modifier = modifier,
        title = catalog.name
    ) { paddingValues ->
        CatalogContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = state,
            viewModel = viewModel,
            navigator = navigator
        )
    }
}