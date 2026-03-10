package com.orka.myfinances.application.viewmodels.home.profile

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.api.office.map
import com.orka.myfinances.data.api.user.UserApi
import com.orka.myfinances.data.models.Company
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.home.viewmodel.profile.ProfileContentModel
import com.orka.myfinances.ui.screens.home.viewmodel.profile.ProfileInteractor
import kotlinx.coroutines.flow.asStateFlow

class ProfileContentViewModel(
    private val officeApi: OfficeApi,
    private val userApi: UserApi,
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
                val offices = officeApi.get(company.id.value)?.map { it.map(company) }
                val user = userApi.getMe()?.map()

                if (offices != null && user != null)
                    setState(State.Success(ProfileContentModel(offices, user)))
                else setState(State.Failure(UiText.Res(R.string.failure)))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
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