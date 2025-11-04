package com.orka.myfinances.ui.screens.basket

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.testLib.amount
import com.orka.myfinances.testLib.id1
import com.orka.myfinances.testLib.id2
import com.orka.myfinances.testLib.price
import com.orka.myfinances.testLib.product1
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BasketScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val apiService = MockProductApiService()
    private val productRepository = ProductRepository(apiService)
    private val repository = BasketRepository(productRepository)
    private val viewModel = BasketScreenViewModel(
        repository = repository,
        logger = logger,
        coroutineScope = testScope
    )

    @Test
    fun `State is Loading`() {
        assertTrue { viewModel.uiState.value is BasketScreenState.Loading }
    }

    @Test
    fun `When initialized gets basket`() = testScope.runTest {
        repository.add(id1, 1)
        repository.add(id1, 1)
        viewModel.initialize()
        testScope.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue {
                state is BasketScreenState.Success
                        && state.items.size == 1
                        && state.price == product1.price * 2
            }
        }
    }

    @Test
    fun `When increase, increases amount of item`() = testScope.runTest {
        repository.add(id1, amount)
        repository.add(id2, amount)
        viewModel.initialize()
        testScope.advanceUntilIdle()
        viewModel.increase(id1)
        testScope.advanceUntilIdle()
        viewModel.increase(id2)
        testScope.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem() as BasketScreenState.Success
            assertEquals(state.items.size, 2)
            assertEquals(price * 4, state.price)
        }
    }

    @Test
    fun `When decrease, decreases amount of item`() = testScope.runTest {
        repository.add(id1, amount)
        repository.add(id2, amount)
        viewModel.initialize()
        testScope.advanceUntilIdle()
        viewModel.increase(id1)
        testScope.advanceUntilIdle()
        viewModel.increase(id2)
        testScope.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem() as BasketScreenState.Success
            assertEquals(state.items.size, 2)
            assertEquals(price * 4, state.price)
        }
    }
}

