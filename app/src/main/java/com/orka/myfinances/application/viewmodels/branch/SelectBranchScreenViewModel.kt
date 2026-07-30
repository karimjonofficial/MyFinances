package com.orka.myfinances.application.viewmodels.branch

import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.viewmodel.sourceful.list.map.MapListViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.ui.screens.branch.SelectBranchScreenInteractor
import com.orka.myfinances.ui.screens.branch.components.BranchUiModel
import kotlinx.coroutines.flow.asStateFlow

class SelectBranchScreenViewModel(
    getBranches: Get<BranchDto>,
    private val getById: GetById<BranchDto>,
    private val sessionManager: SessionManager,
    logger: Logger
) : MapListViewModel<BranchDto, BranchUiModel>(
    get = getBranches,
    map = BranchDto::toUiModel,
    groupBy = { it.name.stickyHeaderKey() },
    logger = logger
), SelectBranchScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun select(id: Id) {
        tryTransition { oldState ->
            val branch = getById.getById(id)
            if (branch != null)
                sessionManager.setBranch(Id(branch.id))
            oldState
        }
    }
}
