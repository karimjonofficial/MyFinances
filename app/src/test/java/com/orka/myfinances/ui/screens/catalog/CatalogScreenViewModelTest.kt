package com.orka.myfinances.ui.screens.catalog

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.testFixtures.DummyLogger
import com.orka.myfinances.testFixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.testFixtures.data.repositories.folder.EmptyFolderRepositoryStub
import com.orka.myfinances.testFixtures.data.repositories.folder.FolderRepositoryStub
import com.orka.myfinances.testFixtures.data.repositories.folder.SpyFolderRepository
import com.orka.myfinances.testFixtures.resources.models.folder.catalog1
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CatalogScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private fun viewModel(repository: FolderRepository): CatalogScreenViewModel {
        return CatalogScreenViewModel(
            catalog = catalog1,
            repository = repository,
            logger = logger,
            coroutineScope = testScope
        )
    }

    @Test
    fun `State is Loading`() {
        val repository = DummyFolderRepository()
        val v = viewModel(repository)
        assertTrue(v.uiState.value is CatalogScreenState.Loading)
    }

    @Test
    fun `When repository fails, state is Failure`() {
        val repository = EmptyFolderRepositoryStub()
        val v = viewModel(repository)

        v.initialize()
        advanceUntilIdle()

        assertTrue(v.uiState.value is CatalogScreenState.Failure)
    }

    @Test
    fun `When repository successes, state is Success`() {
        val repository = FolderRepositoryStub()
        val v = viewModel(repository)

        v.initialize()
        advanceUntilIdle()

        assertTrue(v.uiState.value is CatalogScreenState.Success)
    }

    @Test
    fun `When initializing, gets from repository using catalog`() {
        val repository = SpyFolderRepository()
        val v = viewModel(repository)

        v.initialize()
        advanceUntilIdle()

        assertTrue(catalog1.id == repository.id)
    }
}