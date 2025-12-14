package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.resources.models.credential
import com.orka.myfinances.testFixtures.DummyLogger
import com.orka.myfinances.testFixtures.data.api.company.CompanyApiServiceStub
import com.orka.myfinances.testFixtures.data.api.company.EmptyCompanyApiServiceStub
import com.orka.myfinances.testFixtures.data.api.companyOffice.CompanyOfficeApiServiceStub
import com.orka.myfinances.testFixtures.data.api.companyOffice.EmptyCompanyOfficeApiServiceStub
import com.orka.myfinances.testFixtures.data.api.user.EmptyUserApiServiceStub
import com.orka.myfinances.testFixtures.data.api.user.UserApiServiceStub
import com.orka.myfinances.testFixtures.data.storages.DummySessionStorage
import com.orka.myfinances.testFixtures.data.storages.EmptySessionStorage
import com.orka.myfinances.testFixtures.data.storages.SessionStorageStub
import com.orka.myfinances.testFixtures.data.storages.SpySessionStorage
import com.orka.myfinances.testFixtures.factories.api.ConfigurableApiProvider
import com.orka.myfinances.testFixtures.factories.api.DummyApiProvider
import com.orka.myfinances.ui.managers.session.UiState
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

typealias Storage = LocalSessionStorage
typealias Provider = ApiProvider

class UiManagerTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    private fun uiManager(storage: Storage, provider: Provider): UiManager {
        return UiManager(logger, storage, provider, testScope)
    }

    @Nested
    inner class DummyApiProviderContext {
        private val provider = DummyApiProvider()
        private fun uiManager(storage: LocalSessionStorage): UiManager {
            return uiManager(storage, provider)
        }

        @Test
        fun `State is Guest when session is not found`() = runTest {
            val storage = EmptySessionStorage()
            val manager = uiManager(storage)
            manager.initialize()
            advanceUntilIdle()
            assertTrue(manager.uiState.value is UiState.Guest)
        }

        @Test
        fun `State is SignedIn when session is found`() = runTest {
            val storage = SessionStorageStub()
            val manager = uiManager(storage)
            manager.initialize()
            advanceUntilIdle()
            assertTrue(manager.uiState.value is UiState.SignedIn)
        }
    }

    @Nested
    inner class ConfigurableApiProviderContext {
        private val provider = ConfigurableApiProvider()
        private fun uiManager(storage: LocalSessionStorage): UiManager {
            return UiManager(
                logger = logger,
                storage = storage,
                provider = provider,
                coroutineScope = testScope
            )
        }

        @Nested
        inner class DummySessionStorageContext {
            private val storage = DummySessionStorage()
            private val manager = uiManager(storage)

            @Nested
            inner class EmptyUserApiServiceStubContext {
                @BeforeEach
                fun setup() {
                    provider.setUserApiService(EmptyUserApiServiceStub())
                    provider.setCompanyApiService(CompanyApiServiceStub())
                    provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
                }

                @Test
                fun `When open called and user api service fails, state does not change`() {
                    val state = manager.uiState.value
                    manager.open(credential)
                    advanceUntilIdle()
                    assertTrue(manager.uiState.value === state)
                }

                @Test
                fun `When store called and user api service fails, state does not change`() {
                    val state = manager.uiState.value
                    manager.store(credential)
                    advanceUntilIdle()
                    assertTrue(manager.uiState.value === state)
                }
            }

            @Nested
            inner class EmptyCompanyApiServiceStubContext {
                @BeforeEach
                fun setup() {
                    provider.setUserApiService(UserApiServiceStub())
                    provider.setCompanyApiService(EmptyCompanyApiServiceStub())
                    provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
                }

                @Test
                fun `When open called and company api service fails, state does not change`() {
                    val state = manager.uiState.value
                    manager.open(credential)
                    advanceUntilIdle()
                    assertTrue(manager.uiState.value === state)
                }

                @Test
                fun `When store called and company api service fails, state does not change`() {
                    val state = manager.uiState.value
                    manager.store(credential)
                    advanceUntilIdle()
                    assertTrue(manager.uiState.value === state)
                }
            }

            @Nested
            inner class EmptyCompanyOfficeApiServiceStubContext {
                @BeforeEach
                fun setup() {
                    provider.setUserApiService(UserApiServiceStub())
                    provider.setCompanyApiService(CompanyApiServiceStub())
                    provider.setCompanyOfficeApiService(EmptyCompanyOfficeApiServiceStub())
                }

                @Test
                fun `When open called and companyOffice api service fails, state does not change`() {
                    val state = manager.uiState.value
                    manager.open(credential)
                    advanceUntilIdle()
                    assertTrue(manager.uiState.value === state)
                }

                @Test
                fun `When store called and companyOffice api service fails, state does not change`() {
                    val state = manager.uiState.value
                    manager.store(credential)
                    advanceUntilIdle()
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

                @Test
                fun `When open called and all api services success, state changes into SignedIn`() {
                    manager.open(credential)
                    advanceUntilIdle()
                    assertTrue(manager.uiState.value is UiState.SignedIn)
                }

                @Test
                fun `When store called and all api services success, state changes into SignedIn`() {
                    manager.store(credential)
                    advanceUntilIdle()
                    assertTrue(manager.uiState.value is UiState.SignedIn)
                }
            }
        }

        @Test
        fun `When store called and all api services success, stores into storage`() {
            val storage = SpySessionStorage()
            provider.setUserApiService(UserApiServiceStub())
            provider.setCompanyApiService(CompanyApiServiceStub())
            provider.setCompanyOfficeApiService(CompanyOfficeApiServiceStub())
            val manager = uiManager(storage)

            manager.store(credential)
            advanceUntilIdle()

            assertTrue(credential === storage.credential)
        }
    }
}