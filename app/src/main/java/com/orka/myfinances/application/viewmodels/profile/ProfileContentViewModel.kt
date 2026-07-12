package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.user.GetMe
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.profile.ProfileInteractor
import com.orka.myfinances.ui.screens.profile.models.BranchItemModel
import com.orka.myfinances.ui.screens.profile.models.ProfileContentModel
import kotlinx.coroutines.flow.asStateFlow

class ProfileContentViewModel(
    private val branchId: Id,
    private val getBranches: Get<BranchDto>,
    private val getMe: GetMe,
    private val sessionManager: SessionManager,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<ProfileContentModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val branches: List<BranchDto>? = getBranches.getAll(null)
        val user = getMe.getMe()

        if (branches != null && user != null)
            State.Success(
                value = ProfileContentModel(
                    branches = branches.map { it.toItemModel() },
                    name = "${user.firstName} ${user.lastName}",
                    phone = user.phone,
                    branchName = branches.find { it.id == branchId.value }!!.name
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

    override fun setBranch(branch: BranchItemModel) {
        launch {
            sessionManager.setBranch(branch.id)
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
