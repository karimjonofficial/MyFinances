package com.orka.myfinances.ui.navigation.entries.defaults.category

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.DefaultsSettings
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.settings.defaults.category.DefaultCategoryScreen

fun selectDefaultCategoryEntry(
    modifier: Modifier,
    destination: DefaultsSettings.Category,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val categoryItemsViewModel = viewModel(
        key = "categoryItemsViewModel_${session.branchId.value}",
        initializer = { factory.categoryItemsViewModel() }
    )
    val defaultCategoryViewModel = viewModel(
        key = "defaultCategoryViewModel_${session.branchId.value}",
        initializer = { factory.selectDefaultCategoryViewModel() }
    )

    val categoriesState = categoryItemsViewModel.uiState.collectAsState()
    val selectedState = defaultCategoryViewModel.uiState.collectAsState()

    DefaultCategoryScreen(
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
