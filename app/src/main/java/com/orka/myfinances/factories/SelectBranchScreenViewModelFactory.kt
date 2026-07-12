package com.orka.myfinances.factories

import com.orka.myfinances.application.viewmodels.branch.SelectBranchScreenViewModel
import com.orka.myfinances.data.api.branch.BranchApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.branch.BranchRepository
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.managers.SessionManager
import io.ktor.client.HttpClient

class SelectBranchScreenViewModelFactory(
    private val httpClient: HttpClient,
    private val sessionManager: SessionManager,
    private val loading: UiText,
    private val failure: UiText,
    private val logger: Logger
) {
    fun get(companyId: Id): SelectBranchScreenViewModel {
        val branchApi = BranchApi(httpClient)
        val repository = BranchRepository(companyId, branchApi)
        return SelectBranchScreenViewModel(
            getBranches = repository,
            getById = repository,
            sessionManager = sessionManager,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }
}