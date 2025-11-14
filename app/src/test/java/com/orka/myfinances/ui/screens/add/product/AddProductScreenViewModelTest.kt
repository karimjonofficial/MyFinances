package com.orka.myfinances.ui.screens.add.product

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.data.repositories.StockRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.api.product.SpyProductApiService
import com.orka.myfinances.fixtures.data.api.warehouse.DummyStockApiService
import com.orka.myfinances.fixtures.data.api.warehouse.EmptyStockApiServiceStub
import com.orka.myfinances.fixtures.data.api.warehouse.StockApiServiceStub
import com.orka.myfinances.testLib.addProductRequest
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenState
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AddProductScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun viewModel(
        productApiService: ProductApiService,
        stockApiService: StockApiService
    ): AddProductScreenViewModel {
        return AddProductScreenViewModel(
            productRepository = ProductRepository(productApiService),
            stockRepository = StockRepository(stockApiService),
            logger = logger,
            coroutineScope = testScope
        )
    }

    @Nested
    inner class DummyProductApiContext {
        private fun viewModel(stockApiService: StockApiService): AddProductScreenViewModel {
            val productApiService = DummyProductApiService()
            return viewModel(productApiService, stockApiService)
        }

        @Test
        fun `When initialized, state is Loading`() {
            val viewModel = viewModel(DummyStockApiService())
            assertEquals(AddProductScreenState.Loading, viewModel.uiState.value)
        }

        @Test
        fun `When initializing api fails, state is Failure`() {
            val viewModel = viewModel(EmptyStockApiServiceStub())
            viewModel.initialize()
            advanceUntilIdle()
            assertTrue { viewModel.uiState.value is AddProductScreenState.Failure }
        }

        @Test
        fun `When initializing api successes, state is Success`() {
            val viewModel = viewModel(StockApiServiceStub())
            viewModel.initialize()
            advanceUntilIdle()
            assertTrue { viewModel.uiState.value is AddProductScreenState.Success }
        }
    }

    @Test
    fun `When add product, passes request`() {
        val productApiService = SpyProductApiService()
        val stockApiService = DummyStockApiService()
        val viewModel = viewModel(productApiService, stockApiService)

        viewModel.addProduct(addProductRequest)
        advanceUntilIdle()

        assertTrue { productApiService.addCalled }
    }
}