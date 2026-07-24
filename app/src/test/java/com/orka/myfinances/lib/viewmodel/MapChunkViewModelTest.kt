package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MapChunkViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val getChunk = mockk<GetChunk<String>>()
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    private class TestMapChunkViewModel(
        loading: UiText,
        failure: UiText,
        get: GetChunk<String>,
        logger: Logger
    ) : MapChunkViewModel<String, String>(
        loading = loading,
        failure = failure,
        get = get,
        map = { chunk ->
            ChunkUiModel(
                count = chunk.count,
                pageIndex = chunk.pageIndex,
                nextPageIndex = chunk.nextPageIndex,
                previousPageIndex = chunk.previousPageIndex,
                content = mapOf("All" to chunk.results)
            )
        },
        logger = logger
    ) {
        val uiState = state.asStateFlow()
    }

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf("Item 1")
        )
        coEvery { getChunk.getChunk(10, 1, null) } returns chunk

        val viewModel = TestMapChunkViewModel(loading, failure, getChunk, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Item 1", (state as State.Success).value.content["All"]?.get(0))
    }

    @Test
    fun `initialize failure`() = runTest {
        coEvery { getChunk.getChunk(10, 1, null) } returns null

        val viewModel = TestMapChunkViewModel(loading, failure, getChunk, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
        assertEquals(failure, (state as State.Failure).error)
    }

    @Test
    fun `refresh success`() = runTest {
        val chunk = Chunk(1, 1, null, null, listOf("Item 1"))
        coEvery { getChunk.getChunk(10, 1, null) } returns chunk

        val viewModel = TestMapChunkViewModel(loading, failure, getChunk, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val refreshChunk = Chunk(1, 1, null, null, listOf("Refreshed Item"))
        coEvery { getChunk.getChunk(10, 1, null) } returns refreshChunk

        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Refreshed Item", (state as State.Success).value.content["All"]?.get(0))
    }

    @Test
    fun `loadMore success`() = runTest {
        val chunk1 = Chunk(2, 1, 2, null, listOf("Item 1"))
        coEvery { getChunk.getChunk(10, 1, null) } returns chunk1

        val viewModel = TestMapChunkViewModel(loading, failure, getChunk, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val chunk2 = Chunk(2, 2, null, 1, listOf("Item 2"))
        coEvery { getChunk.getChunk(10, 2, null) } returns chunk2

        viewModel.loadMore()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        val items = (state as State.Success).value.content["All"]
        assertEquals(2, items?.size)
        assertEquals("Item 1", items?.get(0))
        assertEquals("Item 2", items?.get(1))
    }

    @Test
    fun `search triggers refresh with query`() = runTest {
        val chunk = Chunk(1, 1, null, null, listOf("Item 1"))
        coEvery { getChunk.getChunk(10, 1, null) } returns chunk
        
        val viewModel = TestMapChunkViewModel(loading, failure, getChunk, logger)
        viewModel.initialize()
        advanceUntilIdle()

        val searchChunk = Chunk(1, 1, null, null, listOf("Search Result"))
        coEvery { getChunk.getChunk(10, 1, "query") } returns searchChunk

        viewModel.search("query")
        advanceUntilIdle()

        verify { logger.log(any(), "Search query: query") }
        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Search Result", (state as State.Success).value.content["All"]?.get(0))
    }
}
