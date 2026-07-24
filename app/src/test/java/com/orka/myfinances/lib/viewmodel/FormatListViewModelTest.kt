package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FormatListViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val get = mockk<Get<Int>>()
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    private class TestFormatListViewModel(
        loading: UiText,
        failure: UiText,
        get: Get<Int>,
        logger: Logger
    ) : FormatListViewModel<Int, String>(
        loading = loading,
        failure = failure,
        get = get,
        map = { it.toString() },
        logger = logger
    ) {
        val uiState = state.asStateFlow()
    }

    @Test
    fun `initialize success`() = runTest {
        coEvery { get.getAll(null) } returns listOf(1, 2, 3)

        val viewModel = TestFormatListViewModel(loading, failure, get, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("1", "2", "3"), (state as State.Success).value)
    }

    @Test
    fun `initialize failure`() = runTest {
        coEvery { get.getAll(null) } returns null

        val viewModel = TestFormatListViewModel(loading, failure, get, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
        assertEquals(failure, (state as State.Failure).error)
    }

    @Test
    fun `search triggers refresh with query`() = runTest {
        coEvery { get.getAll(null) } returns listOf(1)
        val viewModel = TestFormatListViewModel(loading, failure, get, logger)
        viewModel.initialize()
        advanceUntilIdle()

        coEvery { get.getAll("query") } returns listOf(4, 5)
        viewModel.search("query")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals(listOf("4", "5"), (state as State.Success).value)
    }
}
