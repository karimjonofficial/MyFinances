package com.orka.myfinances.application.manager.ui

import app.cash.turbine.test
import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.repositories.info.InfoRepository
import com.orka.myfinances.data.storages.credentials.CredentialsStorage
import com.orka.myfinances.data.storages.defaults.DefaultsStorage
import com.orka.myfinances.runtime.GuestRuntimeInitializer
import com.orka.myfinances.runtime.NewUserRuntimeInitializer
import com.orka.myfinances.runtime.SignedInRuntimeInitializer
import com.orka.myfinances.testFixtures.logger.DummyLogger
import com.orka.myfinances.testFixtures.resources.models.credentials1
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.ui.screens.host.viewmodel.UiState
import com.orka.myfinances.validators.CredentialsValidator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UiManagerTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val credentialsStorage = mockk<CredentialsStorage>()
    private val validator = mockk<CredentialsValidator>()
    private val defaultsStorage = mockk<DefaultsStorage>()
    private val guestRuntimeInitializer = mockk<GuestRuntimeInitializer>()
    private val newUserRuntimeInitializer = mockk<NewUserRuntimeInitializer>()
    private val signedInRuntimeInitializer = mockk<SignedInRuntimeInitializer>()
    private val infoRepository = mockk<InfoRepository>()
    private val manager = UiManager(
        credentialsStorage = credentialsStorage,
        credentialsValidator = validator,
        defaultsStorage = defaultsStorage,
        guestRuntimeInitializer = guestRuntimeInitializer,
        newUserRuntimeInitializer = newUserRuntimeInitializer,
        signedInRuntimeInitializer = signedInRuntimeInitializer,
        infoRepository = infoRepository,
        logger = logger
    )

    @Test
    fun `Manager starts in Initial state`() = runTest {
        manager.uiState.test {
            assertTrue(awaitItem() is UiState.Initial)
        }
    }

    @Test
    fun `State is Failure when setBranchId called not in NewUser`() = runTest {
        manager.uiState.test {
            awaitItem()
            manager.setBranch(id1)
            assertTrue(awaitItem() is UiState.Failure)
        }
    }

    @Test
    fun `State is Failure when storage throws`() = runTest {
        coEvery { credentialsStorage.get() } throws Exception()

        manager.uiState.test {
            awaitItem()
            manager.initialize()
            assertTrue(awaitItem() is UiState.Failure)
        }
    }

    @Test
    fun `State goes through Loading when refresh is called`() = runTest {
        coEvery { credentialsStorage.get() } returns null
        every { guestRuntimeInitializer.initialize(any()) } returns Unit

        manager.uiState.test {
            awaitItem()
            manager.refresh()
            assertTrue(awaitItem() is UiState.Loading)
            assertTrue(awaitItem() is UiState.Guest)
        }
    }

    @Nested
    inner class `CredentialStorage returns null` {
        @BeforeEach
        fun setup() {
            coEvery { credentialsStorage.get() } returns null
            every { guestRuntimeInitializer.initialize(any()) } returns Unit
        }

        @Test
        fun `State is Guest when storage returns null`() = runTest {
            manager.uiState.test {
                awaitItem()
                manager.initialize()
                assertTrue(awaitItem() is UiState.Guest)
            }
        }

        @Test
        fun `Initializes runtime when storage returns null`() = runTest {
            manager.uiState.test {
                awaitItem()
                manager.initialize()
                awaitItem()
                verify { guestRuntimeInitializer.initialize(any()) }
            }
        }

        @Nested
        inner class `NewUserRuntimeInitializer returns Unit` {
            @BeforeEach
            fun setup() {
                every { newUserRuntimeInitializer.initialize(any(), any()) } returns Unit
                coEvery { infoRepository.getCompanyId(any()) } returns id1
            }


            @Test
            fun `State is NewUser when open called`() = runTest {
                manager.uiState.test {
                    awaitItem()
                    manager.initialize()
                    awaitItem()
                    manager.open(credentials1)
                    assertTrue(awaitItem() is UiState.NewUser)
                }
            }

            @Test
            fun `Calls getCompanyId`() = runTest {
                manager.uiState.test {
                    awaitItem()
                    manager.initialize()
                    awaitItem()
                    manager.open(credentials1)
                    awaitItem()
                    coVerify { infoRepository.getCompanyId(any()) }
                }
            }

            @Test
            fun `Initializes NewUserRuntimeInitializer when open called`() = runTest {
                manager.uiState.test {
                    awaitItem()
                    manager.initialize()
                    awaitItem()
                    manager.open(credentials1)
                    awaitItem()
                    verify { newUserRuntimeInitializer.initialize(any(), any()) }
                }
            }

            @Nested
            inner class `Set of Storage returns Unit` {
                @BeforeEach
                fun setup() {
                    coEvery { credentialsStorage.set(any()) } returns Unit
                    coEvery { defaultsStorage.setDefaultBranchId(any()) } returns Unit
                }

                @Test
                fun `State is NewUser when store called`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        manager.store(credentials1)
                        assertTrue(awaitItem() is UiState.NewUser)
                    }
                }

                @Test
                fun `Set of Storage is called when store called`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        manager.store(credentials1)
                        awaitItem()
                        coVerify { credentialsStorage.set(any()) }
                    }
                }

                @Test
                fun `Calls getCompanyId`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        manager.store(credentials1)
                        awaitItem()
                        coVerify { infoRepository.getCompanyId(any()) }
                    }
                }

                @Test
                fun `Initializes NewUserRuntimeInitializer when store called`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        manager.store(credentials1)
                        awaitItem()
                        verify { newUserRuntimeInitializer.initialize(any(), any()) }
                    }
                }
            }
        }
    }

    @Nested
    inner class `CredentialStorage returns credentials` {
        @BeforeEach
        fun setup() {
            coEvery { credentialsStorage.get() } returns credentials1
        }

        @Nested
        inner class `Validator returns null` {
            @BeforeEach
            fun setup() {
                coEvery { validator.validate(any()) } returns null
                every { guestRuntimeInitializer.initialize(any()) } returns Unit
            }

            @Test
            fun `State is Guest`() = runTest {

                manager.uiState.test {
                    awaitItem()
                    manager.initialize()
                    assertTrue(awaitItem() is UiState.Guest)
                }
            }

            @Test
            fun `Initializes GuestRuntimeInitializer`() = runTest {

                manager.uiState.test {
                    awaitItem()
                    manager.initialize()
                    awaitItem()
                    verify { guestRuntimeInitializer.initialize(any()) }
                }
            }
        }

        @Nested
        inner class `Validator returns credentials` {
            @BeforeEach
            fun setup() {
                coEvery { validator.validate(any()) } returns credentials1
            }

            @Nested
            inner class `getDefaultBranchId returns null` {
                @BeforeEach
                fun setup() {
                    coEvery { defaultsStorage.getDefaultBranchId() } returns null
                    every { newUserRuntimeInitializer.initialize(any(), any()) } returns Unit
                    coEvery { infoRepository.getCompanyId(any()) } returns id1

                }

                @Test
                fun `State is NewUser`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        assertTrue(awaitItem() is UiState.NewUser)
                    }
                }

                @Test
                fun `Calls getCompanyId`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        coVerify { infoRepository.getCompanyId(any()) }
                    }
                }

                @Test
                fun `Initializes NewUserRuntimeInitializer`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        verify { newUserRuntimeInitializer.initialize(any(), any()) }
                    }
                }

                @Nested
                inner class `setBranchId returns Unit` {
                    @BeforeEach
                    fun setup() {
                        coEvery { defaultsStorage.setDefaultBranchId(any()) } returns Unit
                        every { signedInRuntimeInitializer.initialize(any(), any()) } returns Unit
                        coEvery { infoRepository.getCompanyId(any()) } returns id1

                    }

                    @Test
                    fun `State is SignedIn when setBranch is called`() = runTest {
                        manager.uiState.test {
                            awaitItem()
                            manager.initialize()
                            awaitItem()
                            manager.setBranch(id1)
                            assertTrue(awaitItem() is UiState.SignedIn)
                        }
                    }

                    @Test
                    fun `setDefaultBranchId is called when setBranchCalled`() = runTest {
                        manager.uiState.test {
                            awaitItem()
                            manager.initialize()
                            awaitItem()
                            manager.setBranch(id1)
                            awaitItem()
                            coVerify { defaultsStorage.setDefaultBranchId(any()) }
                        }
                    }

                    @Test
                    fun `Calls getCompanyId`() = runTest {
                        manager.uiState.test {
                            awaitItem()
                            manager.initialize()
                            awaitItem()
                            manager.setBranch(id1)
                            awaitItem()
                            coVerify { infoRepository.getCompanyId(any()) }
                        }
                    }

                    @Test
                    fun `Initializes SignedInRuntimeInitializer when setBranchCalled`() = runTest {
                        manager.uiState.test {
                            awaitItem()
                            manager.initialize()
                            awaitItem()
                            manager.setBranch(id1)
                            awaitItem()
                            verify { signedInRuntimeInitializer.initialize(any(), any()) }
                        }
                    }
                }
            }

            @Nested
            inner class `getDefaultBranchId returns id` {
                @BeforeEach
                fun setup() {
                    coEvery { defaultsStorage.getDefaultBranchId() } returns id1
                    every { signedInRuntimeInitializer.initialize(any(), any()) } returns Unit
                    coEvery { infoRepository.getCompanyId(any()) } returns id1
                }

                @Test
                fun `Initializes SignedInRuntimeInitializer`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        verify { signedInRuntimeInitializer.initialize(any(), any()) }
                    }
                }

                @Test
                fun `Calls getCompanyId`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        coVerify { infoRepository.getCompanyId(any()) }
                    }
                }

                @Test
                fun `State is SignedIn`() = runTest {
                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        assertTrue(awaitItem() is UiState.SignedIn)
                    }
                }

                @Test
                fun `State is Loading when setBranch called in SignIn`() = runTest {
                    coEvery { defaultsStorage.setDefaultBranchId(any()) } returns Unit

                    manager.uiState.test {
                        awaitItem()
                        manager.initialize()
                        awaitItem()
                        manager.setBranch(id1)
                        assertTrue(awaitItem() is UiState.Loading)
                        awaitItem()
                    }
                }

                @Nested
                inner class `Clear returns Unit` {
                    @BeforeEach
                    fun setup() {
                        every { guestRuntimeInitializer.initialize(any()) } returns Unit
                        coEvery { defaultsStorage.clear() } returns Unit
                        coEvery { credentialsStorage.clear() } returns Unit
                    }

                    @Test
                    fun `State is Guest when logout called`() = runTest {
                        manager.uiState.test {
                            awaitItem()
                            manager.initialize()
                            awaitItem()
                            manager.logout()
                            assertTrue(awaitItem() is UiState.Guest)
                        }
                    }

                    @Test
                    fun `LogOut calls clear of DefaultsStorage`() = runTest {
                        manager.uiState.test {
                            awaitItem()
                            manager.initialize()
                            awaitItem()
                            manager.logout()
                            awaitItem()
                            coVerify { defaultsStorage.clear() }
                        }
                    }

                    @Test
                    fun `LogOut calls clear of CredentialsStorage`() = runTest {
                        manager.uiState.test {
                            awaitItem()
                            manager.initialize()
                            awaitItem()
                            manager.logout()
                            awaitItem()
                            coVerify { credentialsStorage.clear() }
                        }
                    }
                }
            }
        }
    }
}