package com.orka.myfinances.application.viewmodels.sale.list

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.saleDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SaleContentViewModelTest : MainDispatcherContext() {
    private val getChunk = mockk<GetChunk<SaleDto>>()
    private val searchChunk = mockk<SearchChunk<SaleDto>>()
    private val events = MutableSharedFlow<SaleEvent>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(saleDto1)
        )
        coEvery { getChunk.getChunk(any(), any()) } returns chunk

        val viewModel = SaleContentViewModel(
            getChunk, searchChunk, events, navigator, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
