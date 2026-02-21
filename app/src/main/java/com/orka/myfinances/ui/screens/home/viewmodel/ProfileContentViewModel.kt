package com.orka.myfinances.ui.screens.home.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.ListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

class ProfileContentViewModel(
    loading: UiText,
    failure: UiText,
    get: Get<Office>,
    private val navigator: Navigator,
    logger: Logger
) : ListViewModel<Office>(
    loading = loading,
    failure = failure,
    repository = get,
    logger = logger
) {
    val uiState = state.asStateFlow()

    fun history() {
        launch { navigator.navigateToHistory() }
    }

    fun settings() {
        launch { navigator.navigateToSettings() }
    }

    fun templates() {
        launch { navigator.navigateToTemplates() }
    }

    fun clients() {
        launch { navigator.navigateToClients() }
    }

    fun orders() {
        launch { navigator.navigateToOrders() }
    }

    fun debts() {
        launch { navigator.navigateToDebts() }
    }
}