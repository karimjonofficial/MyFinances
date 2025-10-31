package com.orka.myfinances.ui.screens.add.product

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.StockRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.api.product.SpyProductApiService
import com.orka.myfinances.fixtures.data.api.warehouse.DummyStockApiService
import com.orka.myfinances.fixtures.data.api.warehouse.EmptyStockApiServiceStub
import com.orka.myfinances.fixtures.data.api.warehouse.StockApiServiceStub
import com.orka.myfinances.testLib.addProductRequest
import com.orka.myfinances.testLib.assertStateTransition
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenState
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AddProductScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun viewModel(
        productRepository: ProductRepository,
        stockRepository: StockRepository
    ): AddProductScreenViewModel {
        return AddProductScreenViewModel(
            productRepository = productRepository,
            stockRepository = stockRepository,
            logger = logger,
            coroutineScope = testScope
        )
    }

    @Nested
    inner class DummyProductApiContext {
        private fun viewModel(stockRepository: StockRepository): AddProductScreenViewModel {
            val productRepository = ProductRepository(DummyProductApiService())
            return viewModel(productRepository, stockRepository)
        }

        @Test
        fun `When initialized, state is Loading`() {
            val viewModel = viewModel(StockRepository(DummyStockApiService()))
            assertEquals(AddProductScreenState.Loading, viewModel.uiState.value)
        }

        @Test
        fun `When initializing api fails, state is Failure`() {
            val viewModel = viewModel(StockRepository(EmptyStockApiServiceStub()))
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                assertState = { it is AddProductScreenState.Failure },
                action = { viewModel.initialize() }
            )
        }

        @Test
        fun `When initializing api successes, state is Success`() {
            val viewModel = viewModel(StockRepository(StockApiServiceStub()))
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                assertState = { it is AddProductScreenState.Success },
                action = { viewModel.initialize() }
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When add product, passes request`() {
        val apiService = SpyProductApiService()
        val viewModel = viewModel(
            productRepository = ProductRepository(apiService),
            stockRepository = StockRepository(DummyStockApiService())
        )
        viewModel.addProduct(addProductRequest)
        testScope.advanceUntilIdle()
        assertTrue { apiService.addCalled }
    }
}