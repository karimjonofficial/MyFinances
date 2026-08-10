package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.MapChunkViewModel
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.testLib.MainDispatcherContext
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MapChunkViewModelTest : MainDispatcherContext() {
    private val logger = mockk<Logger>(relaxed = true)
    private val getChunk = mockk<GetChunk<String>>()

    private class TestMapChunkViewModel(
        get: GetChunk<String>,
        logger: Logger
    ) : MapChunkViewModel<String, String>(
        get = get,
        map = { chunk ->
            ChunkUiModel(
                size = chunk.results.size,
                pageIndex = chunk.pageIndex,
                nextPageIndex = chunk.nextPageIndex,
                previousPageIndex = chunk.previousPageIndex,
                content = mapOf("All" to chunk.results)
            )
        },
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
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf("Item 1")
        )
        coEvery { getChunk.getChunk(10, 1) } returns chunk

        val viewModel = TestMapChunkViewModel(getChunk, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Item 1", (state as State.Success).value.content["All"]?.get(0))
    }

    @Test
    fun `initialize failure`() = runTest {
        coEvery { getChunk.getChunk(10, 1) } returns null

        val viewModel = TestMapChunkViewModel(getChunk, logger)
        viewModel.start()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Failure)
    }

    @Test
    fun `refresh success`() = runTest {
        val chunk = Chunk(1, 1, null, null, listOf("Item 1"))
        coEvery { getChunk.getChunk(10, 1) } returns chunk

        val viewModel = TestMapChunkViewModel(getChunk, logger)
        viewModel.start()
        advanceUntilIdle()

        val refreshChunk = Chunk(1, 1, null, null, listOf("Refreshed Item"))
        coEvery { getChunk.getChunk(10, 1) } returns refreshChunk

        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        assertEquals("Refreshed Item", (state as State.Success).value.content["All"]?.get(0))
    }

    @Test
    fun `loadMore success`() = runTest {
        val chunk1 = Chunk(2, 1, 2, null, listOf("Item 1"))
        coEvery { getChunk.getChunk(10, 1) } returns chunk1

        val viewModel = TestMapChunkViewModel(getChunk, logger)
        viewModel.start()
        advanceUntilIdle()

        val chunk2 = Chunk(2, 2, null, 1, listOf("Item 2"))
        coEvery { getChunk.getChunk(1, 2) } returns chunk2

        viewModel.loadMore()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is State.Success)
        val items = (state as State.Success).value.content["All"]
        assertEquals(2, items?.size)
        assertEquals("Item 1", items?.get(0))
        assertEquals("Item 2", items?.get(1))
    }
}
