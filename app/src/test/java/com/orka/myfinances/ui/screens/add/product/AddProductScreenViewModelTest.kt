package com.orka.myfinances.ui.screens.add.product

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.EmptyWarehouseApiServiceStub
import com.orka.myfinances.data.repositories.WarehouseApiServiceStub
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.testLib.assertStateTransition
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenState
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AddProductScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun viewModel(repository: WarehouseRepository): AddProductScreenViewModel {
        return AddProductScreenViewModel(repository, logger, coroutineContext)
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