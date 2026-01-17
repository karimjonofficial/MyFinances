package com.orka.myfinances.ui.screens.home.viewmodel

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.ProductTitleRepository
import com.orka.myfinances.testFixtures.DummyLogger
import com.orka.myfinances.testFixtures.resources.amount
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.id2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BasketContentViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val productRepository = ProductRepository(ProductTitleRepository())
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
            launch {
                repository.add(id1, amount)
            }
        }

        @Test
        fun `When initialized gets basket`() = runTest {
            advanceUntilIdle()
            runAndAdvance { viewModel.initialize() }

            viewModel.uiState.test {
                val state = awaitItem()
                assertTrue(state is BasketState.Success && state.items.size == 1)
            }
            cancelChildren()
        }

        @Nested
        inner class AddProduct2Context {
            @BeforeEach
            fun setup() {
                launch {
                    advanceUntilIdle()
                    repository.add(id2, amount)
                    viewModel.initialize()
                }
            }

            @Test
            fun `When increase, increases amount of item`() = runTest {
                advanceUntilIdle()
                viewModel.increase(id2)

                viewModel.uiState.test {
                    awaitItem()
                    awaitItem()
                    val state = awaitItem() as BasketState.Success
                    assertEquals(amount + 1, state.items[0].amount)
                }
                cancelChildren()
            }
        }

        @Nested
        inner class ViewModelInitializedContext {
            @BeforeEach
            fun setup() {
                launch {
                    advanceUntilIdle()
                    viewModel.initialize()
                }
            }

            @Test
            fun `When decrease, decreases amount of item`() = runTest {
                advanceUntilIdle()
                viewModel.increase(id1)
                viewModel.decrease(id1)

                viewModel.uiState.test {
                    awaitItem()
                    val state = awaitItem() as BasketState.Success
                    assertEquals(1, state.items.size)
                    assertEquals(1, state.items[0].amount)
                }
                cancelChildren()
            }

            @Test
            fun `When decrease, removes item`() = runTest {
                advanceUntilIdle()
                runAndAdvance { viewModel.decrease(id1) }

                viewModel.uiState.test {
                    val state = awaitItem() as BasketState.Success
                    assertEquals(0, state.items.size)
                    assertEquals(0, state.price)
                }
                cancelChildren()
            }
        }
    }
}