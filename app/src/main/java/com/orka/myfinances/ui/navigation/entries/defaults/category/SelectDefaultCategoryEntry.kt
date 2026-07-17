package com.orka.myfinances.ui.navigation.entries.defaults.category

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.defaults.category.SelectDefaultCategory

fun selectDefaultCategoryEntry(
    modifier: Modifier,
    destination: Destination.SelectDefaultCategory,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.selectDefaultCategoryViewModel() }
    val state = viewModel.uiState.collectAsState()

    SelectDefaultCategory(
        modifier = modifier,
        state = state.value,
        interactor = viewModel
    )
}
