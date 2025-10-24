package com.orka.myfinances.ui.screens.add.product

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.api.product.SpyProductApiService
import com.orka.myfinances.fixtures.data.api.warehouse.DummyWarehouseApiService
import com.orka.myfinances.fixtures.data.api.warehouse.EmptyWarehouseApiServiceStub
import com.orka.myfinances.fixtures.data.api.warehouse.WarehouseApiServiceStub
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
        warehouseRepository: WarehouseRepository
    ): AddProductScreenViewModel {
        return AddProductScreenViewModel(
            productRepository = productRepository,
            warehouseRepository = warehouseRepository,
            logger = logger,
            context = coroutineContext
        )
    }

    @Nested
    inner class DummyProductApiContext {
        private fun viewModel(warehouseRepository: WarehouseRepository): AddProductScreenViewModel {
            val productRepository = ProductRepository(DummyProductApiService())
            return viewModel(productRepository, warehouseRepository)
        }

        @Test
        fun `When initialized, state is Loading`() {
            val viewModel = viewModel(WarehouseRepository(DummyWarehouseApiService()))
            assertEquals(AddProductScreenState.Loading, viewModel.uiState.value)
        }

        @Test
        fun `When initializing api fails, state is Failure`() {
            val viewModel = viewModel(WarehouseRepository(EmptyWarehouseApiServiceStub()))
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                assertState = { it is AddProductScreenState.Failure },
                action = { viewModel.initialize() }
            )
        }

        @Test
        fun `When initializing api successes, state is Success`() {
            val viewModel = viewModel(WarehouseRepository(WarehouseApiServiceStub()))
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
            warehouseRepository = WarehouseRepository(DummyWarehouseApiService())
        )
        viewModel.addProduct(addProductRequest)
        testScope.advanceUntilIdle()
        assertTrue { apiService.addCalled }
    }
}