package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.api.office.map
import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.viewmodel.MapperListViewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class SelectOfficeScreenViewModel(
    private val client: HttpClient,
    private val company: Company,
    private val sessionManager: SessionManager,
    private val credentials: Credentials,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapperListViewModel<Office, OfficeUiModel>(
    get = {
        try {
            val response = client.get(
                urlString = "branches/",
                block = { parameter(key = "company", value = company.id.value) }
            )
            if (response.status == HttpStatusCode.OK) {
                response.body<List<OfficeApiModel>>().map { it.map(company) }
            } else null
        } catch (_: Exception) {
            null
        }
    },
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