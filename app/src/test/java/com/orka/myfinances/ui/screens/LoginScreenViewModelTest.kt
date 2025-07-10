package com.orka.myfinances.ui.screens

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyCredentialDataSource
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.DummySessionManager
import com.orka.myfinances.fixtures.SpyCredentialDataSource
import com.orka.myfinances.fixtures.SpySessionManager
import com.orka.myfinances.fixtures.StubCredentialDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LoginScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    @Nested
    inner class DummySessionManagerContext {
        private val manager = DummySessionManager()

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When authorize called gets credentials`() {
            val dataSource = SpyCredentialDataSource()
            val viewmodel = LoginScreenViewModel(logger, dataSource, manager)
            viewmodel.authorize("username", "password")
            testScope.advanceUntilIdle()
            assertTrue(dataSource.called)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When credential not found state is error`() {
            val dataSource = NoCredentialDataSource()
            val viewmodel = LoginScreenViewModel(logger, dataSource, manager)
            viewmodel.authorize("username", "password")
            testScope.advanceUntilIdle()
            val state = viewmodel.uiState.value
            assertTrue(state is LoginScreenState.Error)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When authorizeAndRemember credential not found state is error`() {
            val dataSource = NoCredentialDataSource()
            val viewmodel = LoginScreenViewModel(logger, dataSource, manager)
            viewmodel.authorizeAndRemember("username", "password")
            testScope.advanceUntilIdle()
            val state = viewmodel.uiState.value
            assertTrue(state is LoginScreenState.Error)
        }

        @Nested
        inner class DummyCredentialDataSourceContext {
            private val dataSource = DummyCredentialDataSource()
            private val viewModel = LoginScreenViewModel(logger, dataSource, manager)

            @Test
            fun `State is initial`() {
                val state = viewModel.uiState.value
                assertTrue(state is LoginScreenState.Initial)
            }

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When authorization started state changes to loading`() = testScope.runTest {
                val list = mutableListOf<LoginScreenState>()
                val job = testScope.launch { viewModel.uiState.take(3).collect { list.add(it) } }
                viewModel.authorize("username", "password")
                testScope.advanceUntilIdle()
                job.cancel()
                val index = list.indexOfFirst { it is LoginScreenState.Loading }
                assertTrue(index > -1)
                assertTrue { list[index + 1] is LoginScreenState.Error }
            }

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When authorizeAndRemember started state changes to loading`() = testScope.runTest {
                val list = mutableListOf<LoginScreenState>()
                val job = launch { viewModel.uiState.take(3).collect { list.add(it) } }
                viewModel.authorizeAndRemember("username", "password")
                testScope.advanceUntilIdle()
                job.cancel()
                val index = list.indexOfFirst { it is LoginScreenState.Loading }
                assertTrue(index > -1)
                assertTrue { list[index + 1] is LoginScreenState.Error }
            }
        }
    }

    @Nested
    inner class StubDataSource {
        private val dataSource = StubCredentialDataSource()
        private val manager = SpySessionManager()
        private val viewModel = LoginScreenViewModel(logger, dataSource, manager)

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When authorize successful create session`() {
            viewModel.authorize("username", "password")
            testScope.advanceUntilIdle()
            assertTrue(manager.createCalled)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When authorizeAndRemember called stores session`() {
            viewModel.authorizeAndRemember("username", "password")
            testScope.advanceUntilIdle()
            assertTrue(manager.storeCalled)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When authorize successful state is initial`() {
            viewModel.authorize("username", "password")
            testScope.advanceUntilIdle()
            val state = viewModel.uiState.value
            assertTrue(state is LoginScreenState.Initial)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When authorizeAndRemember successful state is initial`() {
            viewModel.authorizeAndRemember("username", "password")
            testScope.advanceUntilIdle()
            val state = viewModel.uiState.value
            assertTrue(state is LoginScreenState.Initial)
        }
    }
}