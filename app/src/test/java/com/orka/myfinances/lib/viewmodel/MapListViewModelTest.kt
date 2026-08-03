package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.list.map.MapListViewModel
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.testLib.MainDispatcherContext
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MapListViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val get = mockk<Get<String>>()

    private class TestMapListViewModel(
        get: Get<String>,
        logger: Logger
    ) : MapListViewModel<String, String>(
        get = get,
        map = { it },
        groupBy = { "Group" },
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
        coEvery { get.getAll() } returns listOf("A", "B")

        val viewModel = TestMapListViewModel(get, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("A", "B"), (state as State.Success).value["Group"])
    }

    @Test
    fun `refresh success`() = runTest {
        coEvery { get.getAll() } returns listOf("A")
        val viewModel = TestMapListViewModel(get, logger)
        viewModel.start()
        advanceUntilIdle()

        coEvery { get.getAll() } returns listOf("B")
        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("B"), (state as State.Success).value["Group"])
    }
}
