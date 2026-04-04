package com.orka.myfinances.application.viewmodels.office

import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.api.office.map
import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.FormatListViewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.host.SelectOfficeScreenInteractor
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel
import kotlinx.coroutines.flow.asStateFlow

class SelectOfficeScreenViewModel(
    private val officeApi: OfficeApi,
    private val company: Company,
    private val sessionManager: SessionManager,
    private val credentials: Credentials,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : FormatListViewModel<OfficeApiModel, OfficeUiModel>(
    get = { officeApi.get(company.id.value) },
    map = { it.toUiModel() },
    loading = loading,
    failure = failure,
    logger = logger
), SelectOfficeScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun select(id: Id) {
        launch {
            val office = officeApi.getById(id.value)
            if(office != null) {
                sessionManager.setOffice(credentials, office.map(company))//TODO No need for the company
            }
        }
    }
}