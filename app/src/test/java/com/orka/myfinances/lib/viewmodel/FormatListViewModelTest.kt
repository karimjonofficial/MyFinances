package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.list.format.FormatListViewModel
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FormatListViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val get = mockk<Get<Int>>()

    private class TestFormatListViewModel(
        get: Get<Int>,
        logger: Logger
    ) : FormatListViewModel<Int, String>(
        get = get,
        map = { it.toString() },
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
        coEvery { get.getAll() } returns listOf(1, 2, 3)

        val viewModel = TestFormatListViewModel(get, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("1", "2", "3"), (state as State.Success).value)
    }

    @Test
    fun `initialize failure`() = runTest {
        coEvery { get.getAll() } returns null

        val viewModel = TestFormatListViewModel(get, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
    }

    @Test
    fun `refresh success`() = runTest {
        coEvery { get.getAll() } returns listOf(1)
        val viewModel = TestFormatListViewModel(get, logger)
        viewModel.start()
        advanceUntilIdle()

        coEvery { get.getAll() } returns listOf(4, 5)
        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("4", "5"), (state as State.Success).value)
    }
}
