package com.orka.myfinances.application.viewmodels.order.list.incompleted

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.repositories.order.GetOrdersChunk
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.orderDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OrdersListScreenViewModelTest : MainDispatcherContext() {
    private val getOrdersChunk = mockk<GetOrdersChunk>()
    private val events = MutableSharedFlow<OrderEvent>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(orderDto1)
        )
        coEvery { getOrdersChunk.getOrdersChunk(any(), any(), any(), any()) } returns chunk

        val viewModel = OrdersListScreenViewModel(
            getOrdersChunk, events, navigator, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
