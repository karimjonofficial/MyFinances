package com.orka.myfinances.application.viewmodels.stock

import android.util.Log
import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.product.ProductDto
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.basket.MinBasketItem
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.data.repositories.stock.StockRepository
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
import kotlin.time.Instant

class StockItemsContentViewModelTest : MainDispatcherContext() {
    private val stockRepository = mockk<StockRepository>()
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
        every { stockRepository.events } returns stockEvents
        every { basketRepository.events } returns basketEvents
        coEvery { basketRepository.get() } returns emptyList()
        every { formatPrice.formatPrice(any()) } returns "Price"
        every { formatDecimal.formatDecimal(any()) } returns "1.0"
    }

    @Test
    fun `Initializes and shows basket amounts`() = runTest {
        val stockItem = stockItem()

        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(stockItem)
        )

        coEvery { stockRepository.getByCategory(any(), any(), any(), any()) } returns chunk
        coEvery { basketRepository.get() } returns listOf(MinBasketItem(Id(101), 5))

        val viewModel = StockItemsContentViewModel(
            categoryId = categoryId,
            repository = stockRepository,
            basketRepository = basketRepository,
            formatPrice = formatPrice,
            formatDecimal = formatDecimal,
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
        val stockItem = stockItem()

        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(stockItem)
        )

        coEvery { stockRepository.getByCategory(any(), any(), any(), any()) } returns chunk
        coEvery { basketRepository.get() } returns emptyList()

        val viewModel = StockItemsContentViewModel(
            categoryId = categoryId,
            repository = stockRepository,
            basketRepository = basketRepository,
            formatPrice = formatPrice,
            formatDecimal = formatDecimal,
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
            coEvery { basketRepository.get() } returns listOf(MinBasketItem(Id(101), 3))
            basketEvents.emit(BasketEvent.FullRefresh)

            state = awaitItem()
            while (state !is State.Success || (state.value.content.values.flatten().first().model.basketAmount == null)) {
                state = awaitItem()
            }

            item = state.value.content.values.flatten().first()
            assertEquals("1.0", item.model.basketAmount)
        }
    }

    private fun stockItem(): StockItemDto {
        val instant = Instant.fromEpochMilliseconds(0)
        return StockItemDto(
            id = 1,
            product = ProductDto(
                id = 101,
                title = ProductTitleDto(
                    id = 101,
                    category = categoryId.value,
                    name = "Product 101",
                    properties = emptyList(),
                    defaultPrice = 0,
                    defaultSalePrice = 0,
                    defaultExposedPrice = 0,
                    createdAt = instant,
                    modifiedAt = instant,
                    description = null
                ),
                price = 100,
                salePrice = 100,
                exposedPrice = 100,
                createdAt = instant,
                modifiedAt = instant
            ),
            amount = 10,
            dateTime = null,
            createdAt = instant,
            modifiedAt = instant
        )
    }
}
