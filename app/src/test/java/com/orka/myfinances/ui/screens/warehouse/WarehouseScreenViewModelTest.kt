package com.orka.myfinances.ui.screens.warehouse

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.repositories.StockRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.api.product.EmptyProductApiServiceStub
import com.orka.myfinances.fixtures.data.api.product.ProductApiServiceStub
import com.orka.myfinances.fixtures.data.api.product.SpyProductApiService
import com.orka.myfinances.fixtures.data.api.warehouse.DummyStockApiService
import com.orka.myfinances.fixtures.data.api.warehouse.EmptyStockApiServiceStub
import com.orka.myfinances.fixtures.data.api.warehouse.StockApiServiceStub
import com.orka.myfinances.fixtures.resources.models.folder.folder1
import com.orka.myfinances.fixtures.resources.models.stockItem1
import com.orka.myfinances.testLib.addProductRequest
import com.orka.myfinances.testLib.products
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductsState
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseState
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
            warehouse = folder1,
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
            productApiService: ProductApiService,
            stockApiService: StockApiService,
            add: (StockItem) -> Unit
        ): ViewModel {
            val productRepository = ProductRepository(productApiService)
            val stockRepository = StockRepository(stockApiService)
            return viewModel(
                productRepository = productRepository,
                stockRepository = stockRepository,
                add = add
            )
        }

        @Nested
        inner class DummyAddContext {
            private fun viewModel(
                productApiService: ProductApiService,
                stockApiService: StockApiService
            ): ViewModel {
                return viewModel(
                    productApiService = productApiService,
                    stockApiService = stockApiService,
                    add = {}
                )
            }

            @Nested
            inner class DummyProductApiServiceContext {
                private fun viewModel(stockApiService: StockApiService): ViewModel {
                    val productApiService = DummyProductApiService()
                    return viewModel(productApiService, stockApiService)
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
                    private fun viewModel(productApiService: ProductApiService): ViewModel {
                        val stockApiService = DummyStockApiService()
                        return viewModel(productApiService, stockApiService)
                    }

                    @Test
                    fun `When api fails, product state is failure`() = runAndCancelChildren {
                        val productApiService = EmptyProductApiServiceStub()
                        val viewModel = viewModel(productApiService)

                        runAndAdvance { viewModel.initialize() }

                        viewModel.productsState.test {
                            val state = awaitItem()
                            assertTrue (state is ProductsState.Failure)
                        }
                    }

                    @Test
                    fun `When api success, product state is success`() = runAndCancelChildren {
                        val productApiService = ProductApiServiceStub()
                        val viewModel = viewModel(productApiService)

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
                val productApiService = DummyProductApiService()
                val stockApiService = DummyStockApiService()
                var invoked = false

                val viewModel = viewModel(
                    productApiService = productApiService,
                    stockApiService = stockApiService,
                    add = { invoked = true }
                )

                runAndAdvance { viewModel.addToBasket(stockItem1) }

                assertTrue(invoked)
            }
        }

        @Test
        fun `Api triggers initialize`() = runAndCancelChildren {
            val productApiService = SpyProductApiService()
            val stockApiService = DummyStockApiService()
            val productRepository = ProductRepository(productApiService)
            val stockRepository = StockRepository(stockApiService)
            viewModel(
                productRepository = productRepository,
                stockRepository = stockRepository,
                add = {}
            )

            runAndAdvance { productRepository.add(addProductRequest) }

            assertTrue(productApiService.getCalled)
        }
    }