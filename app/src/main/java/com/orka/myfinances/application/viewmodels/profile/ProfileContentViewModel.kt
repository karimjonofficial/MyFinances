package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.api.user.UserApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.company.getAll
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.profile.ProfileInteractor
import com.orka.myfinances.ui.screens.profile.models.OfficeItemModel
import com.orka.myfinances.ui.screens.profile.models.ProfileContentModel
import kotlinx.coroutines.flow.asStateFlow

class ProfileContentViewModel(
    private val officeId: Id,
    private val officeApi: OfficeApi,
    private val userApi: UserApi,
    private val sessionManager: SessionManager,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<ProfileContentModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val offices: List<OfficeApiModel>? = officeApi.getAll()
        val user = userApi.getMe()

        if (offices != null && user != null)
            State.Success(
                value = ProfileContentModel(
                    offices = offices.map { it.toItemModel() },
                    name = "${user.firstName} ${user.lastName}",
                    phone = user.phone,
                    officeName = offices.find { it.id == officeId.value }!!.name
                )
            )
        else null
    },
    logger = logger
), ProfileInteractor {
    val uiState = state.asStateFlow()

    override fun history() {
        launch { navigator.navigateToHistory() }
    }

    override fun settings() {
        launch { navigator.navigateToSettings() }
    }

    override fun setOffice(office: OfficeItemModel) {
        launch {
            sessionManager.setOffice(office.id)
        }
    }

    override fun logout() {
        launch {
            sessionManager.logout()
        }
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