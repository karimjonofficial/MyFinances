package com.orka.myfinances.ui.managers.ui

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.company.CompanyApiServiceStub
import com.orka.myfinances.fixtures.data.api.company.EmptyCompanyApiServiceStub
import com.orka.myfinances.fixtures.data.api.companyOffice.CompanyOfficeApiServiceStub
import com.orka.myfinances.fixtures.data.api.companyOffice.EmptyCompanyOfficeApiServiceStub
import com.orka.myfinances.fixtures.data.api.user.EmptyUserApiServiceStub
import com.orka.myfinances.fixtures.data.api.user.UserApiServiceStub
import com.orka.myfinances.fixtures.data.repositories.DummyFolderRepository
import com.orka.myfinances.fixtures.data.storages.DummySessionStorage
import com.orka.myfinances.fixtures.data.storages.EmptySessionStorage
import com.orka.myfinances.fixtures.data.storages.SessionStorageStub
import com.orka.myfinances.fixtures.data.storages.SpySessionStorage
import com.orka.myfinances.fixtures.factories.ConfigurableApiProvider
import com.orka.myfinances.fixtures.factories.DummyApiProvider
import com.orka.myfinances.testLib.assertStateTransition
import com.orka.myfinances.testLib.credential
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

typealias Storage = LocalSessionStorage
typealias Provider = ApiProvider

class UiManagerTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    @Nested
    inner class DummyApiProviderContext {
        private val provider = DummyApiProvider()

        @Nested
        inner class DummySessionStorageContext {
            private val storage = DummySessionStorage()
            private val manager = uiManager(storage, provider)

            @Test
            fun `Dialog state is null when initialized`() {
                assertTrue { manager.dialogState.value == null }
            }

            @Nested
            inner class HomeScreenViewModelContext {
                val repository = DummyFolderRepository()
                val viewModel = HomeScreenViewModel(logger, repository, coroutineContext)
                val dialog = DialogState.AddFolder(viewModel)

                @Test
                fun `When show is called, then state changes`() {
                    assertStateTransition(
                        stateFlow = manager.dialogState,
                        assertState = { it == dialog },
                        action = { manager.show(dialog) }
                    )
                }

                @Test
                fun `When hide is called, dialog state is null`() {
                    assertStateTransition(
                        stateFlow = manager.dialogState,
                        assertState = { it == null },
                        action = {
                            manager.show(dialog)
                            manager.hide()
                        },
                        skippedSameTransitions = 1
                    )
                }
            }
        }

        @Test
        fun `State is Guest when session is not found`() {
            val storage = EmptySessionStorage()
            val manager = uiManager(storage)

            assertStateTransition(
                stateFlow = manager.uiState,
                assertState = { it is UiState.Guest },
                action = { manager.initialize() }
            )
        }

        @Test
        fun `State is SignedIn when session is found`() {
            val storage = SessionStorageStub()
            val manager = uiManager(storage)

            assertStateTransition(
                stateFlow = manager.uiState,
                assertState = { it is UiState.SignedIn },
                action = { manager.initialize() }
            )
        }

        private fun uiManager(storage: LocalSessionStorage): UiManager {
            return uiManager(storage, provider)
        }
    }

    @Nested
    inner class ConfigurableApiProviderContext {
        private val provider = ConfigurableApiProvider()

        @Nested
        inner class DummySessionStorageContext {
            val storage = DummySessionStorage()

            @Nested
            inner class EmptyUserApiServiceStubContext {
                private val manager = uiManager(storage)

                @BeforeEach
                fun setup() {
                    provider.setUserApiService(EmptyUserApiServiceStub())
                    provider.setCompanyApiService(CompanyApiServiceStub())
                    provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When open called and user api service fails, state does not change`() {
                    val state = manager.uiState.value

                    manager.open(credential)
                    testScope.advanceUntilIdle()

                    assertTrue(manager.uiState.value === state)
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When store called and user api service fails, state does not change`() {
                    val state = manager.uiState.value

                    manager.store(credential)
                    testScope.advanceUntilIdle()

                    assertTrue(manager.uiState.value === state)
                }
            }

            @Nested
            inner class EmptyCompanyApiServiceStubContext {
                private val manager = uiManager(storage)

                @BeforeEach
                fun setup() {
                    provider.setUserApiService(UserApiServiceStub())
                    provider.setCompanyApiService(EmptyCompanyApiServiceStub())
                    provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When open called and company api service fails, state does not change`() {
                    val state = manager.uiState.value

                    manager.open(credential)
                    testScope.advanceUntilIdle()

                    assertTrue(manager.uiState.value === state)
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When store called and company api service fails, state does not change`() {
                    val state = manager.uiState.value

                    manager.store(credential)
                    testScope.advanceUntilIdle()

                    assertTrue(manager.uiState.value === state)
                }
            }

            @Nested
            inner class EmptyCompanyOfficeApiServiceStubContext {
                private val manager = uiManager(storage)

                @BeforeEach
                fun setup() {
                    provider.setUserApiService(UserApiServiceStub())
                    provider.setCompanyApiService(CompanyApiServiceStub())
                    provider.setCompanyOfficeApiService(EmptyCompanyOfficeApiServiceStub())
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When open called and companyOffice api service fails, state does not change`() {
                    val state = manager.uiState.value

                    manager.open(credential)
                    testScope.advanceUntilIdle()

                    assertTrue(manager.uiState.value === state)
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When store called and companyOffice api service fails, state does not change`() {
                    val state = manager.uiState.value

                    manager.store(credential)
                    testScope.advanceUntilIdle()

                    assertTrue(manager.uiState.value === state)
                }
            }

            @Nested
            inner class SuccessContext {
                @BeforeEach
                fun setup() {
                    provider.setUserApiService(UserApiServiceStub())
                    provider.setCompanyApiService(CompanyApiServiceStub())
                    provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When open called and all api services success, state changes into SignedIn`() {
                    val manager = UiManager(logger, storage, provider, coroutineContext)

                    testScope.assertStateTransition(
                        stateFlow = manager.uiState,
                        assertState = { it is UiState.SignedIn },
                        action = {
                            manager.open(credential)
                            testScope.advanceUntilIdle()
                        }
                    )
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When store called and all api services success, state changes into SignedIn`() {
                    val manager = UiManager(logger, storage, provider, coroutineContext)

                    testScope.assertStateTransition(
                        stateFlow = manager.uiState,
                        assertState = { it is UiState.SignedIn },
                        action = {
                            manager.store(credential)
                            testScope.advanceUntilIdle()
                        }
                    )
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When store called and all api services success, stores into storage`() {
            val storage = SpySessionStorage()
            provider.setUserApiService(UserApiServiceStub())
            provider.setCompanyApiService(CompanyApiServiceStub())
            provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
            val manager = UiManager(logger, storage, provider, coroutineContext)

            manager.store(credential)
            testScope.advanceUntilIdle()

            assertTrue(credential === storage.credential)
        }

        private fun uiManager(storage: LocalSessionStorage): UiManager {
            return UiManager(
                logger = logger,
                storage = storage,
                provider = provider,
                context = coroutineContext
            )
        }
    }

    private fun uiManager(storage: Storage, provider: Provider): UiManager {
        return UiManager(logger, storage, provider, coroutineContext)
    }
}