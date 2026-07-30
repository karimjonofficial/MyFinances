package com.orka.myfinances.application.viewmodels.profile

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.user.GetMe
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.testFixtures.resources.dtos.branchDto1
import com.orka.myfinances.testFixtures.resources.dtos.userDto1
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProfileContentViewModelTest : MainDispatcherContext() {
    private val branchId = Id(1)
    private val getBranches = mockk<Get<BranchDto>>()
    private val getMe = mockk<GetMe>()
    private val sessionManager = mockk<SessionManager>(relaxed = true)
    private val navigator = mockk<Navigator>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getBranches.getAll(any()) } returns listOf(branchDto1)
        coEvery { getMe.getMe() } returns userDto1

        val viewModel = ProfileContentViewModel(
            branchId, getBranches, getMe, sessionManager, navigator, loading, failure, logger
        )
        viewModel.initialize()
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
