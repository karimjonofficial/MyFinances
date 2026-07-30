package com.orka.myfinances.application.viewmodels.login

import com.orka.myfinances.data.repositories.auth.Authenticator
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.testFixtures.resources.models.credentials1
import com.orka.myfinances.testLib.MainDispatcherContext
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LoginScreenViewModelTest : MainDispatcherContext() {
    private val authenticator = mockk<Authenticator>()
    private val sessionManager = mockk<SessionManager>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `authorize success`() = runTest {
        coEvery { authenticator.authenticate(any(), any()) } returns credentials1

        val viewModel = LoginScreenViewModel(
            authenticator, sessionManager, logger
        )
        
        viewModel.authorize("user", "pass")
        advanceUntilIdle()

        coVerify { sessionManager.open(credentials1) }
    }

    @Test
    fun `authorizeAndRemember success`() = runTest {
        coEvery { authenticator.authenticate(any(), any()) } returns credentials1

        val viewModel = LoginScreenViewModel(
            authenticator, sessionManager, logger
        )
        
        viewModel.authorizeAndRemember("user", "pass")
        advanceUntilIdle()

        coVerify { sessionManager.store(credentials1) }
    }

    @Test
    fun `authorize failure`() = runTest {
        coEvery { authenticator.authenticate(any(), any()) } returns null

        val viewModel = LoginScreenViewModel(
            authenticator, sessionManager, logger
        )
        
        viewModel.authorize("user", "pass")
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Failure)
    }
}
