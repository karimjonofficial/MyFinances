package com.orka.myfinances.application.viewmodels.basket

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.basket.MinBasketItem
import com.orka.myfinances.data.repositories.stock.GetStockItemByProduct
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.productDto1
import com.orka.myfinances.testFixtures.resources.dtos.stockItemDto1
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.basket.BasketItemUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasketContentViewModelTest : MainDispatcherContext() {
    private val basketRepository = mockk<BasketRepository>()
    private val stockRepository = mockk<GetStockItemByProduct>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val formatPrice = mockk<FormatPrice>()
    private val formatDecimal = mockk<FormatDecimal>()
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val events = MutableSharedFlow<BasketEvent>()

    @Test
    fun `initialize success`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns emptyList()
        every { formatPrice.formatPrice(any()) } returns "0.0"

        val viewModel = createViewModel()
        viewModel.initialize()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }

    @Test
    fun `increase calls repository add`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns emptyList()
        coEvery { basketRepository.add(any(), any()) } returns Unit
        val viewModel = createViewModel()
        val item = mockk<BasketItemUiModel>()
        every { item.productId } returns Id(1)

        viewModel.increase(item)
        advanceUntilIdle()

        coVerify { basketRepository.add(Id(1), 1) }
    }

    @Test
    fun `decrease calls repository remove`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns emptyList()
        coEvery { basketRepository.remove(any(), any()) } returns Unit
        val viewModel = createViewModel()
        val item = mockk<BasketItemUiModel>()
        every { item.productId } returns Id(1)

        viewModel.decrease(item)
        advanceUntilIdle()

        coVerify { basketRepository.remove(Id(1), 1) }
    }

    @Test
    fun `remove calls repository remove with full amount`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns emptyList()
        coEvery { basketRepository.remove(any(), any()) } returns Unit
        val viewModel = createViewModel()
        val item = mockk<BasketItemUiModel>()
        every { item.productId } returns Id(1)
        every { item.amount } returns 5

        viewModel.remove(item)
        advanceUntilIdle()

        coVerify { basketRepository.remove(Id(1), 5) }
    }

    @Test
    fun `clear calls repository clear`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns emptyList()
        coEvery { basketRepository.clear() } returns Unit
        val viewModel = createViewModel()

        viewModel.clear()
        advanceUntilIdle()

        coVerify { basketRepository.clear() }
    }

    @Test
    fun `checkout navigates to checkout`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns emptyList()
        every { formatPrice.formatPrice(any()) } returns "0.0"

        val viewModel = createViewModel()
        viewModel.initialize()
        advanceUntilIdle()

        viewModel.checkout()
        advanceUntilIdle()

        coVerify { navigator.navigateToCheckout() }
    }

    @Test
    fun `AmountChanged event updates state locally`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns listOf(MinBasketItem(Id(productDto1.id), 1))
        coEvery { stockRepository.getByProduct(Id(productDto1.id)) } returns stockItemDto1
        every { formatPrice.formatPrice(any()) } returns "100.0"
        every { formatDecimal.formatDecimal(any()) } returns "1.0"

        val viewModel = createViewModel()
        // Collect uiState to increment subscriptionCount
        val job = launch { viewModel.uiState.collect {} }
        
        viewModel.initialize()
        advanceUntilIdle()

        // Verify initial state
        var state = viewModel.uiState.value as State.Success
        assertEquals("1.0", state.value.items[0].model.amount)

        // Emit AmountChanged
        every { formatDecimal.formatDecimal(2.0) } returns "2.0"
        events.emit(BasketEvent.AmountChanged(Id(productDto1.id), 2))
        advanceUntilIdle()

        state = viewModel.uiState.value as State.Success
        assertEquals("2.0", state.value.items[0].model.amount)
        job.cancel()
    }

    @Test
    fun `ItemRemoved event updates state locally`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns listOf(MinBasketItem(Id(productDto1.id), 1))
        coEvery { stockRepository.getByProduct(Id(productDto1.id)) } returns stockItemDto1
        every { formatPrice.formatPrice(any()) } returns "100.0"
        every { formatDecimal.formatDecimal(any()) } returns "1.0"

        val viewModel = createViewModel()
        val job = launch { viewModel.uiState.collect {} }

        viewModel.initialize()
        advanceUntilIdle()

        // Verify initial state
        var state = viewModel.uiState.value as State.Success
        assertEquals(1, state.value.items.size)

        // Emit ItemRemoved
        events.emit(BasketEvent.ItemRemoved(Id(productDto1.id)))
        advanceUntilIdle()

        state = viewModel.uiState.value as State.Success
        assertTrue(state.value.items.isEmpty())
        job.cancel()
    }

    @Test
    fun `Clear event clears state locally`() = runTest {
        every { basketRepository.events } returns events
        coEvery { basketRepository.get() } returns listOf(MinBasketItem(Id(productDto1.id), 1))
        coEvery { stockRepository.getByProduct(Id(productDto1.id)) } returns stockItemDto1
        every { formatPrice.formatPrice(any()) } returns "100.0"
        every { formatDecimal.formatDecimal(any()) } returns "1.0"

        val viewModel = createViewModel()
        val job = launch { viewModel.uiState.collect {} }

        viewModel.initialize()
        advanceUntilIdle()

        // Emit Clear
        events.emit(BasketEvent.Clear)
        advanceUntilIdle()

        val state = viewModel.uiState.value as State.Success
        assertTrue(state.value.items.isEmpty())
        job.cancel()
    }

    private fun createViewModel() = BasketContentViewModel(
        basketRepository = basketRepository,
        stockRepository = stockRepository,
        navigator = navigator,
        formatPrice = formatPrice,
        formatDecimal = formatDecimal,
        loading = loading,
        failure = failure,
        logger = logger
    )
}
