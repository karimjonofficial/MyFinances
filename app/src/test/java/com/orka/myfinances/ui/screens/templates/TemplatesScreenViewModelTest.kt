package com.orka.myfinances.ui.screens.templates

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.fixtures.data.repositories.template.EmptyTemplateRepositoryStub
import com.orka.myfinances.fixtures.data.repositories.template.TemplateRepositoryStub
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TemplatesScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun viewModel(repository: TemplateRepository): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(repository, logger, coroutineContext)
    }

    @Test
    fun `Initial state is Loading`() {
        val repository = DummyTemplateRepository()
        val viewModel = viewModel(repository)
        assertEquals(TemplatesScreenState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `When repository fails, state is Error`() {
        val repository = EmptyTemplateRepositoryStub()
        val viewModel = viewModel(repository)
        assertStateTransition(
            stateFlow = viewModel.uiState,
            assertState = { it is TemplatesScreenState.Error },
            action = { viewModel.initialize() }
        )
    }

    @Test
    fun `When repository success, state is Success`() {
        val repository = TemplateRepositoryStub()
        val viewModel = viewModel(repository)
        assertStateTransition(
            stateFlow = viewModel.uiState,
            assertState = { it is TemplatesScreenState.Success },
            action = { viewModel.initialize() }
        )
    }
}