package com.orka.myfinances.application.viewmodels.branch

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.branchDto1
import com.orka.myfinances.managers.SessionManager
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SelectBranchScreenViewModelTest : MainDispatcherContext() {
    private val getBranches = mockk<Get<BranchDto>>()
    private val getById = mockk<GetById<BranchDto>>()
    private val sessionManager = mockk<SessionManager>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getBranches.getAll(any()) } returns listOf(branchDto1)

        val viewModel = SelectBranchScreenViewModel(
            getBranches, getById, sessionManager, loading, failure, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }

    @Test
    fun `select branch`() = runTest {
        coEvery { getBranches.getAll(any()) } returns listOf(branchDto1)
        coEvery { getById.getById(any()) } returns branchDto1

        val viewModel = SelectBranchScreenViewModel(
            getBranches, getById, sessionManager, loading, failure, logger
        )
        advanceUntilIdle()

        viewModel.select(Id(branchDto1.id))
        advanceUntilIdle()

        verify { sessionManager.setBranch(Id(branchDto1.id)) }
    }
}
