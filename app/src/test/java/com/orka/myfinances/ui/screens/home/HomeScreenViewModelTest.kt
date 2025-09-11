package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.EmptyFolderRepositoryStub
import com.orka.myfinances.fixtures.data.repositories.FolderRepositoryStub
import com.orka.myfinances.testLib.assertStateTransition
import com.orka.myfinances.testLib.catalogFolderType
import com.orka.myfinances.testLib.name
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HomeScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    @Nested
    inner class DummyFolderRepositoryContext {
        private val repository = DummyFolderRepository()
        private val viewModel = HomeScreenViewModel(repository, logger, coroutineContext)

        @Test
        fun `State is Initial`() {
            val uiState = viewModel.uiState.value
            assertTrue { uiState is HomeScreenState.Initial }
        }

        @Test
        fun `When initialize called state changes to Loading`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                assertState = { it is HomeScreenState.Loading },
                action = { viewModel.initialize() }
            )
        }

        @Test
        fun `When category is added state goes Loading`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.addFolder(name, catalogFolderType) },
                assertState = { it is HomeScreenState.Loading }
            )
        }
    }

    @Nested
    inner class EmptyFolderRepositoryStubContext {
        private val repository = EmptyFolderRepositoryStub()
        private val viewModel = HomeScreenViewModel(repository, logger, coroutineContext)

        @Test
        fun `When data source fails state goes to error`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.initialize() },
                assertState = { it is HomeScreenState.Error }
            )
        }

        @Test
        fun `When add category fails state goes to previous state`() {
            val state = viewModel.uiState.value

            assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.addFolder(name, catalogFolderType) },
                assertState = { it === state },
                skippedSameTransitions = 1
            )
        }
    }

    @Nested
    inner class FolderRepositoryStubContext {
        private val repository = FolderRepositoryStub()
        private val viewModel = HomeScreenViewModel(repository, logger, coroutineContext)

        @Test
        fun `When data source successes state goes to success`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.initialize() },
                assertState = { it is HomeScreenState.Success }
            )
        }

        @Test
        fun `When add category successes state goes to success`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.addFolder(name, catalogFolderType) },
                assertState = { it is HomeScreenState.Success }
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When add folder successes, calls get`() = runTest {
        val repository = SpyFolderRepository()
        val folders = repository.folders
        val viewModel = HomeScreenViewModel(repository, logger, coroutineContext)
        viewModel.addFolder(name, catalogFolderType)
        testScope.advanceUntilIdle()
        val state = viewModel.uiState.value
        assertTrue { state is HomeScreenState.Success }
        assertTrue { (state as HomeScreenState.Success).folders === folders }
    }
}