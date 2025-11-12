package com.orka.myfinances

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.ui.managers.dialog.DialogState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class DialogManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val provider = SpyViewModelProvider()
    private val manager = DialogManagerImpl(provider, logger)

    @Test
    fun `Dialog state is null when initialized`() {
        assertTrue { manager.dialogState.value == null }
    }

    @Test
    fun `When show is called, then state changes`() = testScope.runTest {
        manager.addFolderDialog()
        testScope.advanceUntilIdle()

        manager.dialogState.test {
            val state = awaitItem()
            assertTrue { state is DialogState.AddFolder && state.viewModel === "home"}
        }
    }

    @Test
    fun `When hide is called, dialog state is null`() = testScope.runTest {
        manager.addFolderDialog()
        manager.hide()
        testScope.advanceUntilIdle()

        manager.dialogState.test {
            val state = awaitItem()
            assertNull(state)
        }
    }
}