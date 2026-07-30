package com.orka.myfinances.application.viewmodels.order.list.incompleted

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.repositories.order.GetOrdersChunk
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.format.FormatDate
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatLocalDate
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.orderDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OrdersListScreenViewModelTest : MainDispatcherContext() {
    private val getOrdersChunk = mockk<GetOrdersChunk>()
    private val events = MutableSharedFlow<OrderEvent>()
    private val formatPrice = mockk<FormatPrice>()
    private val formatDate = mockk<FormatDate>()
    private val formatLocalDate = mockk<FormatLocalDate>()
    private val formatDecimal = mockk<FormatDecimal>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

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
        every { formatLocalDate.formatLocalDate(any()) } returns "2024-01-01"
        every { formatPrice.formatPrice(any()) } returns "11,000"
        every { formatDate.formatDate(any()) } returns "01.01.2024"
        every { formatDecimal.formatDecimal(any()) } returns "10.0"

        val viewModel = OrdersListScreenViewModel(
            getOrdersChunk, events, formatDecimal, formatPrice, formatDate, formatLocalDate, navigator, loading, failure, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
