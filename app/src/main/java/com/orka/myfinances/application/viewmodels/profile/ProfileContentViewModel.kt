package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.user.GetMe
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.BaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.profile.ProfileInteractor
import com.orka.myfinances.ui.models.sheet.BranchItemModel
import com.orka.myfinances.ui.models.content.ProfileContentModel
import com.orka.myfinances.ui.statuses.failure.failure
import kotlinx.coroutines.flow.asStateFlow

class ProfileContentViewModel(
    private val branchId: Id,
    private val getBranches: Get<BranchDto>,
    private val getMe: GetMe,
    private val sessionManager: SessionManager,
    private val navigator: Navigator,
    logger: Logger
) : BaseViewModel<ProfileContentModel>(
    produceInitialState = {
        val branches: List<BranchDto>? = getBranches.getAll()
        val user = getMe.getMe()

        if (branches != null && user != null)
            State.Success(
                ProfileContentModel(
                    branches = branches.map { it.toItemModel() },
                    name = "${user.firstName} ${user.lastName}",
                    phone = user.phone,
                    branchName = branches.find { it.id == branchId.value }!!.name
                )
            )
        else State.Failure(failure)
    },
    logger = logger
), ProfileInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

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

    override fun refresh() {
        initialize()
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
