package com.orka.myfinances.ui.navigation.entries.defaults.template

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.DefaultsSettings
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.settings.defaults.template.DefaultTemplateScreen

fun selectDefaultTemplateEntry(
    modifier: Modifier,
    destination: DefaultsSettings.Template,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val templateItemsViewModel = viewModel(
        key = "templateItemsViewModel_${session.branchId.value}",
        initializer = { factory.templateItemsViewModel() }
    )
    val defaultTemplateViewModel = viewModel(
        key = "defaultTemplateViewModel_${session.branchId.value}",
        initializer = { factory.selectDefaultTemplateViewModel() }
    )

    val templatesState = templateItemsViewModel.uiState.collectAsState()
    val selectedState = defaultTemplateViewModel.uiState.collectAsState()

    DefaultTemplateScreen(
        modifier = modifier,
        state = templatesState.value,
        selectedState = selectedState.value,
        interactor = defaultTemplateViewModel,
        loadMore = templateItemsViewModel::loadMore,
        refresh = {
            templateItemsViewModel.refresh()
            defaultTemplateViewModel.refresh()
        }
    )
}
