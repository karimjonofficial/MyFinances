package com.orka.myfinances.ui.screens.home.viewmodel.profile

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.api.office.map
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class ProfileContentViewModel(
    private val client: HttpClient,
    private val company: Company,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), ProfileInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val officesResponse = client.get(
                    urlString = "branches/",
                    block = { parameter(key = "company", value = company.id.value) }
                )
                val offices = if (officesResponse.status == HttpStatusCode.OK)
                    officesResponse.body<List<OfficeApiModel>>().map { it.map(company) }
                else null

                val userResponse = client.get(urlString = "users/me/")
                val user = if (userResponse.status == HttpStatusCode.OK)
                    userResponse.body<UserApiModel>().map()
                else null

                if (offices != null && user != null) {
                    setState(State.Success(ProfileContentModel(offices, user)))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun history() {
        launch { navigator.navigateToHistory() }
    }

    override fun settings() {
        launch { navigator.navigateToSettings() }
    }

    override fun templates() {
        launch { navigator.navigateToTemplates() }
    }

    override fun clients() {
        launch { navigator.navigateToClients() }
    }

    override fun orders() {
        launch { navigator.navigateToOrders() }
    }

    override fun debts() {
        launch { navigator.navigateToDebts() }
    }
}
