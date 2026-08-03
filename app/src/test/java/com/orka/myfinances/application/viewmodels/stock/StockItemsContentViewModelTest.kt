package com.orka.myfinances.application.viewmodels.stock

import android.util.Log
import app.cash.turbine.test
import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.basket.MinBasketItem
import com.orka.myfinances.data.repositories.stock.GetStockItemsByCategory
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.stockItemDto1
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StockItemsContentViewModelTest : MainDispatcherContext() {
    private val getStockByCategory = mockk<GetStockItemsByCategory>()
    private val basketRepository = mockk<BasketRepository>()
    private val stockEvents = MutableSharedFlow<StockEvent>()
    private val basketEvents = MutableSharedFlow<BasketEvent>()
    private val logger = mockk<Logger>(relaxed = true)

    private val categoryId = Id(1)

    @BeforeEach
    fun initTest() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { basketRepository.events } returns basketEvents
        coEvery { basketRepository.get() } returns emptyList()
    }

    @Test
    fun `Initializes and shows basket amounts`() = runTest {
        val stockItem = stockItemDto1

        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(stockItem)
        )

        coEvery { getStockByCategory.getByCategory(any(), any(), any(), any()) } returns chunk
        coEvery { basketRepository.get() } returns listOf(MinBasketItem(Id(stockItem.product.id), 5))

        val viewModel = StockItemsContentViewModel(
            categoryId = categoryId,
            getByCategory = getStockByCategory,
            stockEvents = stockEvents,
            basketRepository = basketRepository,
            logger = logger
        )

        viewModel.uiState.test {
            val state = expectMostRecentItem()
            if (state is State.Success) {
                val item = state.value.content.values.flatten().first()
                assertEquals(5, item.model.basketAmount)
            }
        }
    }

    @Test
    fun `Updates UI when basket changes`() = runTest {
        val stockItem = stockItemDto1

        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(stockItem)
        )

        coEvery { getStockByCategory.getByCategory(any(), any(), any(), any()) } returns chunk
        coEvery { basketRepository.get() } returns emptyList()

        val viewModel = StockItemsContentViewModel(
            categoryId = categoryId,
            getByCategory = getStockByCategory,
            stockEvents = stockEvents,
            basketRepository = basketRepository,
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
            coEvery { basketRepository.get() } returns listOf(MinBasketItem(Id(stockItem.product.id), 3))
            basketEvents.emit(BasketEvent.FullRefresh)

            state = awaitItem()
            while (state !is State.Success || (state.value.content.values.flatten().first().model.basketAmount == null)) {
                state = awaitItem()
            }

            item = state.value.content.values.flatten().first()
            assertEquals(3, item.model.basketAmount)
        }
    }
}
