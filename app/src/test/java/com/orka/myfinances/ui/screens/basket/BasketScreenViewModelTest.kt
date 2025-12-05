package com.orka.myfinances.ui.screens.basket

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.MockProductApiService
import com.orka.myfinances.testLib.amount
import com.orka.myfinances.testLib.id1
import com.orka.myfinances.testLib.id2
import com.orka.myfinances.testLib.price
import com.orka.myfinances.testLib.product1
import com.orka.myfinances.ui.screens.home.viewmodel.BasketState
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BasketScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val apiService = MockProductApiService()
    private val productRepository = ProductRepository(apiService)
    private val repository = BasketRepository(productRepository)
    private val viewModel = BasketContentViewModel(
        repository = repository,
        logger = logger,
        coroutineScope = testScope
    )

    @Test
    fun `State is Loading`() {
        assertTrue(viewModel.uiState.value is BasketState.Loading)
    }

    @Nested
    inner class AddProduct1Context {
        @BeforeEach
        fun setup() {
            testScope.launch {
                repository.add(id1, amount)
                advanceUntilIdle()
            }
        }

        @Test
        fun `When initialized gets basket`() = runTest {
            runAndAdvance {
                repository.add(id1, amount)
                viewModel.initialize()
            }

            viewModel.uiState.test {
                val state = awaitItem()
                assertTrue(
                    state is BasketState.Success
                            && state.items.size == 1
                            && state.price == product1.price * 2 * amount
                )
            }
        }

        @Nested
        inner class AddProduct2Context {
            @BeforeEach
            fun setup() {
                testScope.launch {
                    repository.add(id2, amount)
                    viewModel.initialize()
                    advanceUntilIdle()
                }
            }
            @Test
            fun `When increase, increases amount of item`() = runTest {
                runAndAdvance {
                    viewModel.increase(id1)
                    viewModel.increase(id2)
                }

                viewModel.uiState.test {
                    val state = awaitItem() as BasketState.Success
                    assertEquals(state.items.size, 2)
                    assertEquals(price * amount * 4, state.price)
                }
            }
        }

        @Nested
        inner class ViewModelInitializedContext {
            @BeforeEach
            fun setup() {
                testScope.launch {
                    viewModel.initialize()
                    advanceUntilIdle()
                }
            }

            @Test
            fun `When decrease, decreases amount of item`() = testScope.runTest {
                runAndAdvance {
                    viewModel.increase(id1)
                    viewModel.decrease(id1)
                }

                viewModel.uiState.test {
                    val state = awaitItem() as BasketState.Success
                    assertEquals(1, state.items.size)
                    assertEquals(price * amount, state.price)
                }
            }

            @Test
            fun `When decrease, removes item`() = testScope.runTest {
                runAndAdvance { viewModel.decrease(id1) }

                viewModel.uiState.test {
                    val state = awaitItem() as BasketState.Success
                    assertEquals(0, state.items.size)
                    assertEquals(0, state.price)
                }
            }
        }
    }
}

