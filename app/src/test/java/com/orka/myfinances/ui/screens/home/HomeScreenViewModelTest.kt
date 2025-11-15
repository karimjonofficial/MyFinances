package com.orka.myfinances.ui.screens.home

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.folder.FolderType
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.folder.EmptyFolderRepositoryStub
import com.orka.myfinances.fixtures.data.repositories.folder.FolderRepositoryStub
import com.orka.myfinances.fixtures.data.repositories.folder.SpyFolderRepository
import com.orka.myfinances.testLib.assertLoadingTransition
import com.orka.myfinances.testLib.name
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HomeScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private fun viewModel(repository: FolderRepository): HomeScreenViewModel {
        return HomeScreenViewModel(
            repository = repository,
            logger = logger,
            coroutineScope = testScope
        )
    }

    @Nested
    inner class DummyFolderRepositoryContext {
        private val repository = DummyFolderRepository()
        private val viewModel = viewModel(repository)
        private suspend fun assertLoadingTransition(action: () -> Unit) =
            viewModel.uiState.assertLoadingTransition<HomeScreenState, HomeScreenState.Loading>(action)

        @Test
        fun `State is Initial`() {
            val uiState = viewModel.uiState.value
            assertTrue(uiState is HomeScreenState.Initial)
        }

        @Test
        fun `When initialize called state changes to Loading`() = runTest {
            assertLoadingTransition { viewModel.initialize() }
        }

        @Test
        fun `When category is added state goes Loading`() = runTest {
            assertLoadingTransition { viewModel.addFolder(name, FolderType.Catalog) }
        }
    }

    @Nested
    inner class EmptyFolderRepositoryStubContext {
        private val repository = EmptyFolderRepositoryStub()
        private val viewModel = viewModel(repository)

        @Test
        fun `When data source fails state goes to error`() = runTest {
            runAndAdvance { viewModel.initialize() }

            viewModel.uiState.test {
                assertTrue(awaitItem() is HomeScreenState.Error)
            }
        }

        @Test
        fun `When add category fails state goes to previous state`() = runTest {
            val state = viewModel.uiState.value

            runAndAdvance { viewModel.addFolder(name, FolderType.Catalog) }

            viewModel.uiState.test {
                assertTrue(awaitItem() === state)
            }
        }
    }

    @Nested
    inner class FolderRepositoryStubContext {
        private val repository = FolderRepositoryStub()
        private val viewModel = viewModel(repository)

        @Test
        fun `When data source successes state goes to success`() = runTest {
            runAndAdvance { viewModel.initialize() }

            viewModel.uiState.test {
                assertTrue(awaitItem() is HomeScreenState.Success)
            }
        }

        @Test
        fun `When add category successes state goes to success`() = runTest {
            runAndAdvance { viewModel.addFolder(name, FolderType.Catalog) }

            viewModel.uiState.test {
                assertTrue(awaitItem() is HomeScreenState.Success)
            }
        }
    }

    @Test
    fun `When add folder successes, calls get`() = runTest {
        val repository = SpyFolderRepository()
        val folders = repository.folders
        val viewModel = viewModel(repository)

        runAndAdvance { viewModel.addFolder(name, FolderType.Catalog) }

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is HomeScreenState.Success && state.folders === folders)
        }
    }
}