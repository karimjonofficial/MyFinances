package com.orka.myfinances.ui.navigation.entries.defaults.category

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.DefaultsSettings
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.settings.defaults.category.SelectDefaultCategory

fun selectDefaultCategoryEntry(
    modifier: Modifier,
    destination: DefaultsSettings.Category,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val categoryItemsViewModel = viewModel(
        key = "categoryItemsViewModel",
        initializer = { factory.categoryItemsViewModel() }
    )
    val defaultCategoryViewModel = viewModel(
        key = "defaultCategoryViewModel",
        initializer = { factory.selectDefaultCategoryViewModel() }
    )

    val categoriesState = categoryItemsViewModel.uiState.collectAsState()
    val selectedState = defaultCategoryViewModel.uiState.collectAsState()

    SelectDefaultCategory(
        modifier = modifier,
        state = categoriesState.value,
        selectedState = selectedState.value,
        interactor = defaultCategoryViewModel,
        refresh = {
            categoryItemsViewModel.refresh()
            defaultCategoryViewModel.refresh()
        }
    )
}
