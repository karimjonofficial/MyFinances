package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.ui.managers.dialog.DialogState
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull

class DialogManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val provider = SpyViewModelProvider()
    private val manager = DialogManagerImpl(provider, logger)

    @Test
    fun `Dialog state is null when initialized`() {
        assertTrue { manager.dialogState.value == null }
    }

    @Test
    fun `When show is called, then state changes`() {
        manager.addFolderDialog()
        advanceUntilIdle()
        val state = manager.dialogState.value
        assertTrue(state is DialogState.AddFolder && state.viewModel === "home")
    }

    @Test
    fun `When hide is called, dialog state is null`() {
        manager.addFolderDialog()
        manager.hide()
        advanceUntilIdle()
        assertNull(manager.dialogState.value)
    }
}