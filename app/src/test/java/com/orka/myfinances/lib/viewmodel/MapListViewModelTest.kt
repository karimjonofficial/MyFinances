package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.list.map.MapListViewModel
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
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    private class TestMapListViewModel(
        loading: UiText,
        failure: UiText,
        get: Get<String>,
        logger: Logger
    ) : MapListViewModel<String, String>(
        loading = loading,
        failure = failure,
        get = get,
        map = { list -> mapOf("Group" to list) },
        logger = logger
    ) {
        val uiState = state.asStateFlow()
    }

    @Test
    fun `initialize success`() = runTest {
        coEvery { get.getAll(null) } returns listOf("A", "B")

        val viewModel = TestMapListViewModel(loading, failure, get, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("A", "B"), (state as State.Success).value["Group"])
    }

    @Test
    fun `search triggers refresh`() = runTest {
        coEvery { get.getAll(null) } returns listOf("A")
        val viewModel = TestMapListViewModel(loading, failure, get, logger)
        viewModel.initialize()
        advanceUntilIdle()

        coEvery { get.getAll("query") } returns listOf("B")
        viewModel.search("query")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("B"), (state as State.Success).value["Group"])
    }
}
