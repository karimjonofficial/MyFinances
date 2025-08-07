package com.orka.myfinances.ui.screens.login

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.managers.DummySessionManager
import com.orka.myfinances.fixtures.managers.SpySessionManager
import com.orka.myfinances.fixtures.data.api.credential.DummyCredentialApiService
import com.orka.myfinances.fixtures.data.api.credential.NoCredentialApiService
import com.orka.myfinances.fixtures.data.api.credential.SpyCredentialApiService
import com.orka.myfinances.fixtures.data.api.credential.StubCredentialApiService
import com.orka.myfinances.testLib.assertStateTransition
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
            val apiService = SpyCredentialApiService()
            val viewmodel = LoginScreenViewModel(logger, apiService, manager)
            viewmodel.authorize("username", "password")
            testScope.advanceUntilIdle()
            assertTrue(apiService.called)
        }

        @Nested
        inner class NoCredentialApiServiceContext {
            private val apiService = NoCredentialApiService()

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When credential not found state is error`() {
                val viewmodel = LoginScreenViewModel(logger, apiService, manager)

                testScope.assertStateTransition(
                    stateFlow = viewmodel.uiState,
                    assertState = { it is LoginScreenState.Error },
                    action = { viewmodel.authorize("username", "password") }
                )
            }

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When authorizeAndRemember credential not found state is error`() {
                val viewmodel = LoginScreenViewModel(logger, apiService, manager)

                testScope.assertStateTransition(
                    stateFlow = viewmodel.uiState,
                    assertState = { it is LoginScreenState.Error },
                    action = { viewmodel.authorizeAndRemember("username", "password") }
                )
            }
        }

        @Nested
        inner class DummyCredentialDataSourceContext {
            private val apiService = DummyCredentialApiService()
            private val viewModel = LoginScreenViewModel(logger, apiService, manager)

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
                    assertState = { it is LoginScreenState.Loading },
                    action = { viewModel.authorize("username", "password") }
                )
            }

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When authorizeAndRemember started state changes to loading`() {
                testScope.assertStateTransition(
                    stateFlow = viewModel.uiState,
                    assertState = { it is LoginScreenState.Loading },
                    action = { viewModel.authorizeAndRemember("username", "password") }
                )
            }
        }
    }

    @Nested
    inner class StubDataSource {
        private val apiService = StubCredentialApiService()
        private val manager = SpySessionManager()
        private val viewModel = LoginScreenViewModel(logger, apiService, manager)

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