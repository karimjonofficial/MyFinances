package com.orka.myfinances.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.settings.home.PinnedCategoriesScreen

fun pinnedCategoriesEntry(
    modifier: Modifier = Modifier,
    destination: Destination.PinnedCategories,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val categoryItemsViewModel = viewModel(
        key = "categoryItemsViewModel",
        initializer = { factory.categoryItemsViewModel() }
    )
    val selectedViewModel = viewModel(
        key = "selected",
        initializer = { factory.selectedCategoriesViewModel() }
    )
    val categoriesState = categoryItemsViewModel.uiState.collectAsState()
    val selectedState = selectedViewModel.uiState.collectAsState()

    PinnedCategoriesScreen(
        modifier = modifier,
        selectedState = selectedState.value,
        state = categoriesState.value,
        interactor = selectedViewModel,
        refresh = {
            categoryItemsViewModel.refresh()
            selectedViewModel.refresh()
        }
    )
}