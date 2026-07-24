package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MapSingleViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val getById = mockk<GetById<String>>()
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val testId = Id(1)

    private class TestMapSingleViewModel(
        id: Id,
        get: GetById<String>,
        loading: UiText,
        failure: UiText,
        logger: Logger
    ) : MapSingleViewModel<String, Int>(
        id = id,
        get = get,
        map = { it.length },
        loading = loading,
        failure = failure,
        logger = logger
    ) {
        val uiState = state.asStateFlow()
    }

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(testId) } returns "Hello"

        val viewModel = TestMapSingleViewModel(testId, getById, loading, failure, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(5, (state as State.Success).value)
    }

    @Test
    fun `initialize failure`() = runTest {
        coEvery { getById.getById(testId) } returns null

        val viewModel = TestMapSingleViewModel(testId, getById, loading, failure, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
        assertEquals(failure, (state as State.Failure).error)
    }

    @Test
    fun `refresh success`() = runTest {
        coEvery { getById.getById(testId) } returns "Initial"

        val viewModel = TestMapSingleViewModel(testId, getById, loading, failure, logger)
        viewModel.initialize()
        advanceUntilIdle()

        coEvery { getById.getById(testId) } returns "Refreshed"
        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(9, (state as State.Success).value)
    }
}
