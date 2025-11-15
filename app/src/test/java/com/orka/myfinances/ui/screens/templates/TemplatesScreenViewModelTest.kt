package com.orka.myfinances.ui.screens.templates

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.fixtures.data.repositories.template.EmptyTemplateRepositoryStub
import com.orka.myfinances.fixtures.data.repositories.template.TemplateRepositoryStub
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TemplatesScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun viewModel(repository: TemplateRepository): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(repository, logger, testScope)
    }

    @Test
    fun `Initial state is Loading`() {
        val repository = DummyTemplateRepository()
        val viewModel = viewModel(repository)
        assertEquals(TemplatesScreenState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `When repository fails, state is Error`() = testScope.runTest {
        val repository = EmptyTemplateRepositoryStub()
        val viewModel = viewModel(repository)

        viewModel.initialize()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is TemplatesScreenState.Error)
    }

    @Test
    fun `When repository success, state is Success`() {
        val repository = TemplateRepositoryStub()
        val viewModel = viewModel(repository)

        viewModel.initialize()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is TemplatesScreenState.Success)
    }
}