package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.FailureStatus
import com.orka.myfinances.lib.viewmodel.base.BaseViewModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BaseViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)

    private class TestBaseViewModel(
        produceSuccess: suspend () -> State<String>,
        logger: Logger
    ) : BaseViewModel<String>(produceSuccess, ExceptionMapper.Default(), logger) {
        val uiState = state.asStateFlow()

        fun start() {
            initialize()
        }
    }

    @Test
    fun `initialize success`() = runTest {
        val produceSuccess = mockk<suspend () -> State<String>>()
        coEvery { produceSuccess() } returns State.Success("Success Data")

        val viewModel = TestBaseViewModel(produceSuccess, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Success Data", (state as State.Success).value)
    }

    @Test
    fun `initialize exception results in failure`() = runTest {
        val produceSuccess = mockk<suspend () -> State<String>>()
        coEvery { produceSuccess() } throws Exception("Error Message")

        val viewModel = TestBaseViewModel(produceSuccess, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
        val status = (state as State.Failure).status
        assertTrue(status is FailureStatus.Exception)
        assertEquals("Error Message",  (status as FailureStatus.Exception).message)
    }
}
