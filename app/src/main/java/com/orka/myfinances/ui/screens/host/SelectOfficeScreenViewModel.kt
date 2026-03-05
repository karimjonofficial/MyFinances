package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.viewmodel.MapperListViewModel
import com.orka.myfinances.ui.managers.SessionManager
import kotlinx.coroutines.flow.asStateFlow

class SelectOfficeScreenViewModel(
    private val sessionManager: SessionManager,
    private val credentials: Credentials,
    get: Get<Office>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapperListViewModel<Office, OfficeUiModel>(
    get = get,
    map = { it.toUiModel() },
    loading = loading,
    failure = failure,
    logger = logger
), ListViewModel<OfficeUiModel> {
    override val uiState = state.asStateFlow()

    fun select(office: Office) {
        launch {
            sessionManager.setOffice(credentials, office)
        }
    }
}