package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DialogManagerImplTest : MainDispatcherContext() {

    private val logger = DummyLogger()

    private val repository = DummyFolderRepository()
    private val manager = DialogManagerImpl(coroutineContext, logger)
    private val viewModel = HomeScreenViewModel(repository, logger, coroutineContext)
    private val dialog = DialogState.AddFolder(viewModel)

    @Test
    fun `Dialog state is null when initialized`() {
        assertTrue { manager.dialogState.value == null }
    }

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