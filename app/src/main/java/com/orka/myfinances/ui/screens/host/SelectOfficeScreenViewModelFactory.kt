package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.application.viewmodels.office.SelectOfficeScreenViewModel
import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.managers.SessionManager
import io.ktor.client.HttpClient

class SelectOfficeScreenViewModelFactory(
    private val httpClient: HttpClient,
    private val sessionManager: SessionManager,
    private val loading: UiText,
    private val failure: UiText,
    private val logger: Logger
) {
    fun get(companyId: Id): SelectOfficeScreenViewModel {
        val officeApi = OfficeApi(companyId, httpClient)
        return SelectOfficeScreenViewModel(
            officeApi = officeApi,
            sessionManager = sessionManager,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }
}