package com.orka.myfinances.application.viewmodels.branch

import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapListViewModel
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.ui.screens.branch.SelectBranchScreenInteractor
import com.orka.myfinances.ui.screens.branch.components.BranchUiModel
import kotlinx.coroutines.flow.asStateFlow

class SelectBranchScreenViewModel(
    getBranches: Get<BranchDto>,
    private val getById: GetById<BranchDto>,
    private val sessionManager: SessionManager,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapListViewModel<BranchDto, BranchUiModel>(
    loading = loading,
    failure = failure,
    get = getBranches,
    map = { branches ->
        branches.sortedBy { it.name }
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { (_, list) -> list.map { it.toUiModel() } }
    },
    logger = logger
), SelectBranchScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun select(id: Id) {
        launch {
            val branch: BranchDto? = getById.getById(id)
            if (branch != null) {
                sessionManager.setBranch(Id(branch.id))
            }
        }
    }
}
