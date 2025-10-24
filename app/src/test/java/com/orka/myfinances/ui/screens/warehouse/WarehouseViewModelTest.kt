package com.orka.myfinances.ui.screens.warehouse

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.repositories.WarehouseApiService
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.api.product.EmptyProductApiServiceStub
import com.orka.myfinances.fixtures.data.api.warehouse.DummyWarehouseApiService
import com.orka.myfinances.fixtures.data.api.warehouse.EmptyWarehouseApiServiceStub
import com.orka.myfinances.fixtures.data.api.warehouse.WarehouseApiServiceStub
import com.orka.myfinances.fixtures.data.api.product.ProductApiServiceStub
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenProductsState
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenWarehouseState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class WarehouseViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun viewModel(
        productApiService: ProductApiService,
        warehouseApiService: WarehouseApiService
    ): WarehouseScreenViewModel {
        val productRepository = ProductRepository(productApiService)
        val warehouseRepository = WarehouseRepository(warehouseApiService)
        return WarehouseScreenViewModel(
            productRepository,
            warehouseRepository,
            logger,
            coroutineContext
        )
    }

    @Nested
    inner class DummyProductApiServiceContext {
        private fun viewModel(warehouseApiService: WarehouseApiService): WarehouseScreenViewModel {
            val productApiService = DummyProductApiService()
            return viewModel(productApiService, warehouseApiService)
        }

        @Test
        fun `When created states are Loading`() {
            val warehouseApiService = DummyWarehouseApiService()
            val viewModel = viewModel(warehouseApiService)
            assertTrue { viewModel.productsState.value is WarehouseScreenProductsState.Loading }
            assertTrue { viewModel.warehouseState.value is WarehouseScreenWarehouseState.Loading }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When api fails, warehouse state is failure`() = testScope.runTest {
            val warehouseApiService = EmptyWarehouseApiServiceStub()
            val viewModel = viewModel(warehouseApiService)

            viewModel.initialize()
            testScope.advanceUntilIdle()

            viewModel.productsState.test {
                val state = awaitItem()
                assertTrue { state is WarehouseScreenProductsState.Failure }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When api success, warehouse state is success`() = testScope.runTest {
            val warehouseApiService = WarehouseApiServiceStub()
            val viewModel = viewModel(warehouseApiService)

            viewModel.initialize()
            testScope.advanceUntilIdle()

            viewModel.warehouseState.test {
                val state = awaitItem()
                assertTrue { state is WarehouseScreenWarehouseState.Success }
            }
        }
    }

    @Nested
    inner class DummyWarehouseApiServiceContext {
        private fun viewModel(productApiService: ProductApiService): WarehouseScreenViewModel {
            val warehouseApiService = DummyWarehouseApiService()
            return viewModel(productApiService, warehouseApiService)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When api fails, product state is failure`() = testScope.runTest {
            val productApiService = EmptyProductApiServiceStub()
            val viewModel = viewModel(productApiService)
            viewModel.initialize()
            testScope.advanceUntilIdle()
            viewModel.productsState.test {
                val state = awaitItem()
                assertTrue { state is WarehouseScreenProductsState.Failure }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When api success, product state is success`() = testScope.runTest {
            val productApiService = ProductApiServiceStub()
            val viewModel = viewModel(productApiService)

            viewModel.initialize()
            testScope.advanceUntilIdle()

            viewModel.productsState.test {
                val state = awaitItem()
                assertTrue { state is WarehouseScreenProductsState.Success }
            }
        }
    }
}