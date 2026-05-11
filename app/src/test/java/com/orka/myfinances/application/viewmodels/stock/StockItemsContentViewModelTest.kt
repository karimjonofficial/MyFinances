package com.orka.myfinances.application.viewmodels.stock

import android.util.Log
import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.api.stock.getByCategory
import com.orka.myfinances.data.api.stock.models.StockItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.Chunk
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

class StockItemsContentViewModelTest : MainDispatcherContext() {
    private val stockApi = mockk<StockApi>()
    private val basketRepository = mockk<BasketRepository>()
    private val formatPrice = mockk<FormatPrice>()
    private val formatDecimal = mockk<FormatDecimal>()
    private val stockEvents = MutableSharedFlow<StockEvent>()
    private val basketEvents = MutableSharedFlow<BasketEvent>()
    private val logger = mockk<Logger>(relaxed = true)

    private val categoryId = Id(1)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    @BeforeEach
    fun initTest() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        mockkStatic("com.orka.myfinances.data.api.stock.GetByCategoryKt")
        every { basketRepository.events } returns basketEvents
        every { basketRepository.getCounts() } returns emptyMap()
        every { formatPrice.formatPrice(any()) } returns "Price"
        every { formatDecimal.formatDecimal(any()) } returns "1.0"
    }

    @Test
    fun `Initializes and shows basket amounts`() = runTest {
        val stockItem = mockk<StockItemApiModel>(relaxed = true)
        every { stockItem.product.id } returns 101
        every { stockItem.product.title.name } returns "Product 101"
        
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(stockItem)
        )

        coEvery { stockApi.getByCategory(any(), any(), any()) } returns chunk
        every { basketRepository.getCounts() } returns mapOf(Id(101) to 5)

        val viewModel = StockItemsContentViewModel(
            categoryId = categoryId,
            stockApi = stockApi,
            basketRepository = basketRepository,
            formatPrice = formatPrice,
            formatDecimal = formatDecimal,
            stockEvents = stockEvents,
            loading = loading,
            failure = failure,
            logger = logger
        )

        viewModel.uiState.test {
            val state = expectMostRecentItem()
            if (state is State.Success) {
                val item = state.value.content.values.flatten().first()
                assertEquals("1.0", item.model.basketAmount)
            }
        }
    }

    @Test
    fun `Updates UI when basket changes`() = runTest {
        val stockItem = mockk<StockItemApiModel>(relaxed = true)
        every { stockItem.product.id } returns 101
        every { stockItem.product.title.name } returns "Product 101"

        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(stockItem)
        )

        coEvery { stockApi.getByCategory(any(), any(), any()) } returns chunk
        every { basketRepository.getCounts() } returns emptyMap()

        val viewModel = StockItemsContentViewModel(
            categoryId = categoryId,
            stockApi = stockApi,
            basketRepository = basketRepository,
            formatPrice = formatPrice,
            formatDecimal = formatDecimal,
            stockEvents = stockEvents,
            loading = loading,
            failure = failure,
            logger = logger
        )

        viewModel.uiState.test {
            var state = awaitItem()
            while (state !is State.Success) {
                state = awaitItem()
            }
            
            var item = state.value.content.values.flatten().first()
            assertEquals(null, item.model.basketAmount)

            // Update basket
            every { basketRepository.getCounts() } returns mapOf(Id(101) to 3)
            basketEvents.emit(BasketEvent)

            state = awaitItem()
            while (state !is State.Success || (state.value.content.values.flatten().first().model.basketAmount == null)) {
                state = awaitItem()
            }

            item = state.value.content.values.flatten().first()
            assertEquals("1.0", item.model.basketAmount)
        }
    }
}
