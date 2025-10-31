package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.ui.managers.dialog.DialogState
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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
        assertStateTransition(
            stateFlow = manager.dialogState,
            assertState = { it is DialogState.AddFolder && it.viewModel === "home" },
            action = { manager.addFolderDialog() }
        )
    }

    @Test
    fun `When hide is called, dialog state is null`() {
        assertStateTransition(
            stateFlow = manager.dialogState,
            assertState = { it == null },
            action = {
                manager.addFolderDialog()
                manager.hide()
            },
            skippedSameTransitions = 1
        )
    }
}