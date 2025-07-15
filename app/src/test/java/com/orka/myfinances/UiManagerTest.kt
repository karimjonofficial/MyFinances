package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.datasources.session.DummySessionDataSource
import com.orka.myfinances.fixtures.datasources.session.NoSessionSessionDataSource
import com.orka.myfinances.fixtures.datasources.session.SpySessionDataSource
import com.orka.myfinances.fixtures.datasources.session.StubSessionDataSource
import com.orka.myfinances.lib.assertStateTransition
import com.orka.myfinances.models.Credential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UiManagerTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `State is Guest when session is not found`() {
        val dataSource = NoSessionSessionDataSource()
        val manager = UiManager(logger, dataSource)

        testScope.assertStateTransition(
            stateFlow = manager.uiState,
            isDesiredState = { it is UiState.Guest },
            action = { manager.initialize() }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `State is SignedIn when session is found`() {
        val dataSource = StubSessionDataSource()
        val manager = UiManager(logger, dataSource)

        testScope.assertStateTransition(
            stateFlow = manager.uiState,
            isDesiredState = { it is UiState.SignedIn },
            action = { manager.initialize() }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When store session is called stores into dataSource`() {
        val dataSource = SpySessionDataSource()
        val manager = UiManager(logger, dataSource)
        val credential = Credential(1)
        manager.storeSession(credential)
        testScope.advanceUntilIdle()
        assertTrue(dataSource.storeCalled)
    }


    @Nested
    inner class DummyDataSourceContext {
        private val dataSource = DummySessionDataSource()
        private val manager = UiManager(logger, dataSource)

        @Test
        fun `Initial state is Initial`() {
            val state = manager.uiState.value
            assertTrue(state is UiState.Initial)
        }

        @Test
        fun `When create session is called state changes into SignedIn`() {
            testScope.assertStateTransition(
                stateFlow = manager.uiState,
                isDesiredState = { it is UiState.SignedIn },
            ) {
                val credential = Credential(1)
                manager.createSession(credential)
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When store session is called state changes into SignedIn`() {
            testScope.assertStateTransition(
                stateFlow = manager.uiState,
                isDesiredState = { it is UiState.SignedIn }
            ) {
                val credential = Credential(1)
                manager.storeSession(credential)
            }
        }
    }
}