package com.orka.myfinances.ui.screens.products.add.viewmodel

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.ProductTitleRepository
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.testFixtures.DummyLogger
import com.orka.myfinances.testFixtures.data.api.warehouse.DummyStockApiService
import com.orka.myfinances.testFixtures.data.api.warehouse.EmptyStockApiServiceStub
import com.orka.myfinances.testFixtures.data.api.warehouse.StockApiServiceStub
import com.orka.myfinances.testFixtures.resources.successfulAddProductRequest
import com.orka.myfinances.testFixtures.resources.models.product.productTitle1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AddProductScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val repository = ProductRepository(repository = ProductTitleRepository())

    private fun viewModel(stockApiService: StockApiService): AddProductScreenViewModel {
        return AddProductScreenViewModel(
            productRepository = repository,
            stockRepository = StockRepository(stockApiService),
            logger = logger,
            coroutineScope = testScope
        )
    }

    @Nested
    inner class DummyProductApiContext {

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
            assertTrue(viewModel.uiState.value is AddProductScreenState.Failure)
        }

        @Test
        fun `When initializing api successes, state is Success`() {
            val viewModel = viewModel(StockApiServiceStub())
            viewModel.initialize()
            advanceUntilIdle()
            assertTrue(viewModel.uiState.value is AddProductScreenState.Success)
        }
    }

    @Test
    fun `When add product, passes request`() = runTest {
        val stockApiService = DummyStockApiService()
        val viewModel = viewModel(stockApiService)
        val response1 = repository.get()

        viewModel.addProduct(
            titleId = productTitle1.id,
            properties = successfulAddProductRequest.properties,
            name = successfulAddProductRequest.name,
            price = successfulAddProductRequest.price,
            salePrice = successfulAddProductRequest.salePrice,
            description = successfulAddProductRequest.description,
            category = productTitle1.category
        )
        advanceUntilIdle()

        val response2 = repository.get()
        assertEquals(if (response1 == null) 1 else response1.size + 1, response2?.size ?: 0)
    }
}