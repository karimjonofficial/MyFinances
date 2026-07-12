package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BaseViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    private class TestBaseViewModel(
        produceSuccess: suspend () -> State.Success<String>?,
        loading: UiText,
        failure: UiText,
        logger: Logger
    ) : BaseViewModel<String>(produceSuccess, loading, failure, logger) {
        val uiState = state.asStateFlow()
    }

    @Test
    fun `initialize success`() = runTest {
        val produceSuccess = mockk<suspend () -> State.Success<String>?>()
        coEvery { produceSuccess() } returns State.Success("Success Data")

        val viewModel = TestBaseViewModel(produceSuccess, loading, failure, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Success Data", (state as State.Success).value)
    }

    @Test
    fun `initialize returns null success results in failure`() = runTest {
        val produceSuccess = mockk<suspend () -> State.Success<String>?>()
        coEvery { produceSuccess() } returns null

        val viewModel = TestBaseViewModel(produceSuccess, loading, failure, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
        assertEquals(failure, (state as State.Failure).error)
    }

    @Test
    fun `initialize exception results in failure`() = runTest {
        val produceSuccess = mockk<suspend () -> State.Success<String>?>()
        coEvery { produceSuccess() } throws Exception("Error Message")

        val viewModel = TestBaseViewModel(produceSuccess, loading, failure, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
        val error = (state as State.Failure).error
        assertTrue(error is UiText.Str)
        assertEquals("Error Message",  (error as UiText.Str).value)
    }

    @Test
    fun `refresh success`() = runTest {
        val produceSuccess = mockk<suspend () -> State.Success<String>?>()
        coEvery { produceSuccess() } returns State.Success("Initial")

        val viewModel = TestBaseViewModel(produceSuccess, loading, failure, logger)
        viewModel.initialize()
        advanceUntilIdle()

        coEvery { produceSuccess() } returns State.Success("Refreshed")
        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Refreshed", (state as State.Success).value)
    }
}
