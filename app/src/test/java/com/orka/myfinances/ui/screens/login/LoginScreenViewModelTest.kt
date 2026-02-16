package com.orka.myfinances.ui.screens.login

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.testFixtures.DummyLogger
import com.orka.myfinances.testFixtures.data.api.credential.CredentialApiStub
import com.orka.myfinances.testFixtures.data.api.credential.DummyCredentialApi
import com.orka.myfinances.testFixtures.data.api.credential.EmptyCredentialApiStub
import com.orka.myfinances.testFixtures.data.api.credential.SpyCredentialApi
import com.orka.myfinances.testFixtures.managers.DummySessionManager
import com.orka.myfinances.testFixtures.managers.SpySessionManager
import com.orka.myfinances.testFixtures.resources.password
import com.orka.myfinances.testFixtures.resources.username
import com.orka.myfinances.testLib.assertLoadingTransition
import com.orka.myfinances.ui.managers.SessionManager
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

typealias ViewModel = LoginScreenViewModel
typealias ApiService = CredentialApi

class LoginScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private fun viewModel(apiService: ApiService, manager: SessionManager): ViewModel {
        return ViewModel(
            apiService = apiService,
            manager = manager,
            logger = logger
        )
    }

    @Nested
    inner class DummySessionManagerContext {
        private val manager = DummySessionManager()
        private fun viewModel(apiService: ApiService): ViewModel {
            return viewModel(apiService, manager)
        }

        @Test
        fun `When authorize called gets credentials`() {
            val apiService = SpyCredentialApi()
            val viewModel = viewModel(apiService)

            viewModel.authorize(username, password)
            advanceUntilIdle()

            assertTrue(apiService.called)
        }

        @Nested
        inner class NoApiServiceContext {
            private val apiService = EmptyCredentialApiStub()
            private val viewModel = viewModel(apiService)

            @Test
            fun `When credential not found state is Error`() {
                viewModel.authorize(username, password)
                advanceUntilIdle()
                assertTrue(viewModel.uiState.value is LoginScreenState.Error)
            }

            @Test
            fun `When authorizeAndRemember credential not found state is Error`() {
                viewModel.authorizeAndRemember(username, password)
                advanceUntilIdle()
                assertTrue(viewModel.uiState.value is LoginScreenState.Error)
            }
        }

        @Nested
        inner class DummyCredentialDataSourceContext {
            private val apiService = DummyCredentialApi()
            private val viewModel = viewModel(apiService)
            private suspend fun assertLoadingTransition(action: () -> Unit) =
                viewModel.uiState.assertLoadingTransition<LoginScreenState, LoginScreenState.Loading>(action)

            @Test
            fun `State is initial`() {
                val state = viewModel.uiState.value
                assertTrue(state is LoginScreenState.Initial)
            }

            @Test
            fun `When authorization started state changes to Loading`() = runTest {
                assertLoadingTransition { viewModel.authorize(username, password) }
            }

            @Test
            fun `When authorizeAndRemember started state changes to Loading`() = runTest {
                assertLoadingTransition { viewModel.authorizeAndRemember(username, password) }
            }
        }
    }

    @Nested
    inner class StubDataSourceContext {
        private val apiService = CredentialApiStub()
        private val manager = SpySessionManager()
        private val viewModel = viewModel(apiService, manager)

        @Nested
        inner class AuthorizeCalledContext {
            @BeforeEach
            fun setup() {
                viewModel.authorize(username, password)
                advanceUntilIdle()
            }

            @Test
            fun `When authorize successful create session`() = runTest {
                assertTrue(manager.createCalled)
            }

            @Test
            fun `When authorize successful state is initial`() = runTest {
                val state = viewModel.uiState.value
                assertTrue(state is LoginScreenState.Initial)
            }
        }

        @Nested
        inner class AuthorizeAndRememberCalledContext {
            @BeforeEach
            fun setup() {
                viewModel.authorizeAndRemember(username, password)
                advanceUntilIdle()
            }

            @Test
            fun `When authorizeAndRemember called stores session`() {
                assertTrue(manager.storeCalled)
            }

            @Test
            fun `When authorizeAndRemember successful state is initial`() {
                val state = viewModel.uiState.value
                assertTrue(state is LoginScreenState.Initial)
            }
        }
    }
}