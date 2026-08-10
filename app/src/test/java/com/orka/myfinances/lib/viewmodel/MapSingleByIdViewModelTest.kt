package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MapSingleByIdViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val getById = mockk<GetById<String>>()
    private val testId = Id(1)

    private class TestMapSingleViewModel(
        id: Id,
        get: GetById<String>,
        logger: Logger
    ) : MapSingleByIdViewModel<String, Int>(
        id = id,
        get = get,
        map = { it.length },
        exceptionMapper = NetworkExceptionMapper(),
        logger = logger
    ) {
        val uiState = state.asStateFlow()

        fun start() {
            initialize()
        }
    }

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(testId) } returns "Hello"

        val viewModel = TestMapSingleViewModel(testId, getById, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(5, (state as State.Success).value)
    }

    @Test
    fun `initialize failure`() = runTest {
        coEvery { getById.getById(testId) } returns null

        val viewModel = TestMapSingleViewModel(testId, getById, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
    }

    @Test
    fun `refresh success`() = runTest {
        coEvery { getById.getById(testId) } returns "Initial"

        val viewModel = TestMapSingleViewModel(testId, getById, logger)
        viewModel.start()
        advanceUntilIdle()

        coEvery { getById.getById(testId) } returns "Refreshed"
        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(9, (state as State.Success).value)
    }
}
