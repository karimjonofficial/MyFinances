package com.orka.myfinances.ui.screens.templates

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.testFixtures.DummyLogger
import com.orka.myfinances.testFixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.testFixtures.data.repositories.template.EmptyTemplateRepositoryStub
import com.orka.myfinances.testFixtures.data.repositories.template.TemplateRepositoryStub
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TemplatesScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun viewModel(repository: TemplateRepository): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(repository, MutableSharedFlow(), logger, testScope)
    }

    @Test
    fun `Initial state is Loading`() {
        val repository = DummyTemplateRepository()
        val viewModel = viewModel(repository)
        assertEquals(TemplatesScreenState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `When repository fails, state is Error`() = runTest {
        val repository = EmptyTemplateRepositoryStub()
        val viewModel = viewModel(repository)

        viewModel.initialize()

        viewModel.uiState.test {
            awaitItem()
            assertTrue(awaitItem() is TemplatesScreenState.Error)
        }
        testScope.coroutineContext.cancelChildren()
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