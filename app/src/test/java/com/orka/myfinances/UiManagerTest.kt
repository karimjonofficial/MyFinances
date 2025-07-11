package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.models.Credential
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.datasources.session.DummySessionDataSource
import com.orka.myfinances.fixtures.datasources.session.NoSessionSessionDataSource
import com.orka.myfinances.fixtures.datasources.session.SpySessionDataSource
import com.orka.myfinances.fixtures.datasources.session.StubSessionDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UiManagerTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `State is Guest when session is not found`() {
        val dataSource = NoSessionSessionDataSource()
        val manager = UiManager(logger, dataSource)
        manager.initialize()
        testScope.advanceUntilIdle()
        val state = manager.uiState.value
        assertTrue(state is UiState.Guest)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `State is SignedIn when session is found`() {
        val dataSource = StubSessionDataSource()
        val manager = UiManager(logger, dataSource)
        manager.initialize()
        testScope.advanceUntilIdle()
        val state = manager.uiState.value
        assertTrue(state is UiState.SignedIn)
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
            val credential = Credential(1)
            manager.createSession(credential)
            val state = manager.uiState.value
            assertTrue(state is UiState.SignedIn)
            if (state is UiState.SignedIn) {
                assertEquals(credential, state.session.credential)
            } else {
                fail("State is not Signed in")
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When store session is called state changes into SignedIn`() {
            val credential = Credential(1)
            manager.storeSession(credential)
            testScope.advanceUntilIdle()
            val state = manager.uiState.value
            assertTrue(state is UiState.SignedIn)
            if (state is UiState.SignedIn) {
                assertEquals(credential, state.session.credential)
            } else {
                fail("State is not Signed in")
            }
        }
    }
}

