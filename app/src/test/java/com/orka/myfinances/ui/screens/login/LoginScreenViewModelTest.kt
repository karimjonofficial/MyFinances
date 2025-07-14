package com.orka.myfinances.ui.screens.login

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.DummySessionManager
import com.orka.myfinances.fixtures.SpySessionManager
import com.orka.myfinances.fixtures.datasources.credential.DummyCredentialDataSource
import com.orka.myfinances.fixtures.datasources.credential.NoCredentialDataSource
import com.orka.myfinances.fixtures.datasources.credential.SpyCredentialDataSource
import com.orka.myfinances.fixtures.datasources.credential.StubCredentialDataSource
import com.orka.myfinances.lib.assertStateTransition
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
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

            testScope.assertStateTransition(
                stateFlow = viewmodel.uiState,
                isDesiredState = { it is LoginScreenState.Error },
                action = { viewmodel.authorize("username", "password") }
            )
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When authorizeAndRemember credential not found state is error`() {
            val dataSource = NoCredentialDataSource()
            val viewmodel = LoginScreenViewModel(logger, dataSource, manager)

            testScope.assertStateTransition(
                stateFlow = viewmodel.uiState,
                isDesiredState = { it is LoginScreenState.Error },
                action = { viewmodel.authorizeAndRemember("username", "password") }
            )
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
            fun `When authorization started state changes to loading`() {
                testScope.assertStateTransition(
                    stateFlow = viewModel.uiState,
                    isDesiredState = { it is LoginScreenState.Loading },
                    action = { viewModel.authorize("username", "password") }
                )
            }

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When authorizeAndRemember started state changes to loading`() {
                testScope.assertStateTransition(
                    stateFlow = viewModel.uiState,
                    isDesiredState = { it is LoginScreenState.Loading },
                    action = { viewModel.authorizeAndRemember("username", "password") }
                )
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