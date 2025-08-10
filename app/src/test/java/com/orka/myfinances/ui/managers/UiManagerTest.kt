package com.orka.myfinances.ui.managers

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.company.CompanyApiServiceStub
import com.orka.myfinances.fixtures.data.api.companyOffice.CompanyOfficeApiServiceStub
import com.orka.myfinances.fixtures.data.api.company.EmptyCompanyApiServiceStub
import com.orka.myfinances.fixtures.data.api.companyOffice.EmptyCompanyOfficeApiServiceStub
import com.orka.myfinances.fixtures.data.api.user.EmptyUserApiServiceStub
import com.orka.myfinances.fixtures.data.api.user.UserApiServiceStub
import com.orka.myfinances.fixtures.data.storages.DummySessionStorage
import com.orka.myfinances.fixtures.data.storages.EmptySessionStorage
import com.orka.myfinances.fixtures.data.storages.SpySessionStorage
import com.orka.myfinances.fixtures.data.storages.SessionStorageStub
import com.orka.myfinances.fixtures.factories.ConfigurableApiProvider
import com.orka.myfinances.testLib.assertStateTransition
import com.orka.myfinances.testLib.credential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UiManagerTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val provider = ConfigurableApiProvider()

    @Test
    fun `State is Guest when session is not found`() {
        val storage = EmptySessionStorage()
        val manager = UiManager(logger, storage, provider, testScope.coroutineContext)
        testScope.assertStateTransition(
            stateFlow = manager.uiState,
            assertState = { it is UiState.Guest },
            action = { manager.initialize() }
        )
    }

    @Test
    fun `State is SignedIn when session is found`() {
        val storage = SessionStorageStub()
        val manager = UiManager(logger, storage, provider, testScope.coroutineContext)

        testScope.assertStateTransition(
            stateFlow = manager.uiState,
            assertState = { it is UiState.SignedIn },
            action = { manager.initialize() }
        )
    }

    @Nested
    inner class DummySessionStorageContext {
        val storage = DummySessionStorage()

        @Nested
        inner class EmptyUserApiServiceStubContext {
            private val userApiService = EmptyUserApiServiceStub()

            @BeforeEach
            fun setup() {
                provider.setUserApiService(userApiService)
            }

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When open called and user api service fails, state does not change`() {
                val manager = UiManager(logger, storage, provider, testScope.coroutineContext)
                val state = manager.uiState.value
                manager.open(credential)
                testScope.advanceUntilIdle()
                assertTrue(manager.uiState.value === state)
            }

            @OptIn(ExperimentalCoroutinesApi::class)
            @Test
            fun `When store called and user api service fails, state does not change`() {
                val manager =
                    UiManager(logger, storage, provider, testScope.coroutineContext)
                val state = manager.uiState.value
                manager.store(credential)
                testScope.advanceUntilIdle()
                assertTrue(manager.uiState.value === state)
            }

            @Nested
            inner class EmptyCompanyApiServiceStubContext {
                private val companyApiService = EmptyCompanyApiServiceStub()

                @BeforeEach
                fun setup() {
                    provider.setUserApiService(UserApiServiceStub())
                    provider.setCompanyApiService(companyApiService)
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun ` open called and company api service fails, state does not change`() {
                    val manager = UiManager(logger, storage, provider, testScope.coroutineContext)
                    val state = manager.uiState.value
                    manager.open(credential)
                    testScope.advanceUntilIdle()
                    assertTrue(manager.uiState.value === state)
                }

                @OptIn(ExperimentalCoroutinesApi::class)
                @Test
                fun `When store called and company api service fails, state does not change`() {
                    val manager = UiManager(logger, storage, provider, testScope.coroutineContext)
                    val state = manager.uiState.value
                    manager.store(credential)
                    testScope.advanceUntilIdle()
                    assertTrue(manager.uiState.value === state)
                }

                @Nested
                inner class EmptyCompanyOfficeApiServiceStubContext {

                    @BeforeEach
                    fun setup() {
                        provider.setCompanyApiService(CompanyApiServiceStub())
                        provider.setCompanyOfficeApiService(EmptyCompanyOfficeApiServiceStub())
                    }

                    @OptIn(ExperimentalCoroutinesApi::class)
                    @Test
                    fun `When open called and companyOffice api service fails, state does not change`() {
                        val manager = UiManager(logger, storage, provider, testScope.coroutineContext)
                        val state = manager.uiState.value
                        manager.open(credential)
                        testScope.advanceUntilIdle()
                        assertTrue(manager.uiState.value === state)
                    }

                    @OptIn(ExperimentalCoroutinesApi::class)
                    @Test
                    fun `When store called and companyOffice api service fails, state does not change`() {
                        val manager = UiManager(logger, storage, provider, testScope.coroutineContext)
                        val state = manager.uiState.value
                        manager.store(credential)
                        testScope.advanceUntilIdle()
                        assertTrue(manager.uiState.value === state)
                    }
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When open called and all api services success, state changes into SignedIn`() {
            provider.setUserApiService(UserApiServiceStub())
            provider.setCompanyApiService(CompanyApiServiceStub())
            provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
            val manager = UiManager(logger, storage, provider, testScope.coroutineContext)

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
            provider.setUserApiService(UserApiServiceStub())
            provider.setCompanyApiService(CompanyApiServiceStub())
            provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
            val manager = UiManager(logger, storage, provider, testScope.coroutineContext)

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When store called and all api services success, stores into storage`() {
        provider.setUserApiService(UserApiServiceStub())
        provider.setCompanyApiService(CompanyApiServiceStub())
        provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
        val storage = SpySessionStorage()
        val manager = UiManager(logger, storage, provider, testScope.coroutineContext)

        manager.store(credential)
        testScope.advanceUntilIdle()

        assertTrue(credential === storage.credential)
    }
}