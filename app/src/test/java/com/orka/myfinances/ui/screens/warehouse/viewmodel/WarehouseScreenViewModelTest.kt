package com.orka.myfinances.ui.screens.warehouse.viewmodel

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.ProductTitleRepository
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.testFixtures.DummyLogger
import com.orka.myfinances.testFixtures.data.api.warehouse.DummyStockApiService
import com.orka.myfinances.testFixtures.data.api.warehouse.EmptyStockApiServiceStub
import com.orka.myfinances.testFixtures.data.api.warehouse.StockApiServiceStub
import com.orka.myfinances.testFixtures.resources.successfulAddProductRequest
import com.orka.myfinances.testFixtures.resources.models.folder.category1
import com.orka.myfinances.testFixtures.resources.models.product.products
import com.orka.myfinances.testFixtures.resources.models.stock.stockItem
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

typealias ViewModel = WarehouseScreenViewModel

class WarehouseScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private fun viewModel(
        productRepository: ProductRepository,
        stockRepository: StockRepository,
        add: (StockItem) -> Unit
    ): ViewModel {
        return ViewModel(
            category = category1,
            productRepository = productRepository,
            stockRepository = stockRepository,
            add = add,
            logger = logger,
            coroutineScope = testScope
        )
    }

    @Nested
    inner class NoRepositoryContext {
        private fun viewModel(
            stockApiService: StockApiService,
            add: (StockItem) -> Unit
        ): ViewModel {
            val productRepository = ProductRepository(ProductTitleRepository())
            val stockRepository = StockRepository(stockApiService)
            return viewModel(
                productRepository = productRepository,
                stockRepository = stockRepository,
                add = add
            )
        }

        @Nested
        inner class DummyAddContext {
            private fun viewModel(stockApiService: StockApiService): ViewModel {
                return viewModel(
                    stockApiService = stockApiService,
                    add = {}
                )
            }

            @Nested
            inner class DummyProductApiServiceContext {
                private fun viewModel(stockApiService: StockApiService): ViewModel {
                    return viewModel(stockApiService)
                }

                @Test
                fun `When created states are Loading`() {
                    val stockApiService = DummyStockApiService()
                    val viewModel = viewModel(stockApiService)
                    assertTrue(viewModel.productsState.value is ProductsState.Loading)
                    assertTrue(viewModel.warehouseState.value is WarehouseState.Loading)
                }

                @Test
                fun `When api fails, warehouse state is failure`() = runAndCancelChildren {
                    val stockApiService = EmptyStockApiServiceStub()
                    val viewModel = viewModel(stockApiService)
                    viewModel.initialize()
                    advanceUntilIdle()
                    assertTrue(viewModel.warehouseState.value is WarehouseState.Failure)
                }

                    @Test
                    fun `When api success, warehouse state is success`() = runAndCancelChildren {
                        val stockApiService = StockApiServiceStub()
                        val viewModel = viewModel(stockApiService)
                        viewModel.initialize()
                        advanceUntilIdle()
                        assertTrue(viewModel.warehouseState.value is WarehouseState.Success)
                    }
                }

                @Nested
                inner class DummyWarehouseApiServiceContext {
                    private fun viewModel(): ViewModel {
                        val stockApiService = DummyStockApiService()
                        return viewModel(stockApiService)
                    }

                    @Test
                    fun `When api fails, product state is failure`() = runAndCancelChildren {
                        val viewModel = viewModel()

                        runAndAdvance { viewModel.initialize() }

                        viewModel.productsState.test {
                            val state = awaitItem()
                            assertTrue (state is ProductsState.Failure)
                        }
                    }

                    @Test
                    fun `When api success, product state is success`() = runAndCancelChildren {
                        val viewModel = viewModel()

                        runAndAdvance { viewModel.initialize() }

                        viewModel.productsState.test {
                            val state = awaitItem()
                            assertTrue(
                                state is ProductsState.Success
                                    && state.products === products
                            )
                        }
                    }
                }
            }

            @Test
            fun `Invokes add to basket`() = runAndCancelChildren {
                val stockApiService = DummyStockApiService()
                var invoked = false

                val viewModel = viewModel(
                    stockApiService = stockApiService,
                    add = { invoked = true }
                )

                runAndAdvance { viewModel.addToBasket(stockItem) }

                assertTrue(invoked)
            }
        }

        @Test
        fun `Api triggers initialize`() = runAndCancelChildren {
            val stockApiService = DummyStockApiService()
            val productRepository = ProductRepository(ProductTitleRepository())
            val stockRepository = StockRepository(stockApiService)
            val viewModel = viewModel(
                productRepository = productRepository,
                stockRepository = stockRepository,
                add = {}
            )

            runAndAdvance { productRepository.add(successfulAddProductRequest) }

            viewModel.productsState.test {
                assertNotNull(awaitItem())
                awaitComplete()
            }
        }
    }