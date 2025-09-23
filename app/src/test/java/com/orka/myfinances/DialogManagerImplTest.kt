package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.factories.ViewModelProviderImpl
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DialogManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val repository = DummyFolderRepository()
    private val templateRepository = DummyTemplateRepository()
    private val homeScreenViewModel = HomeScreenViewModel(repository, logger, coroutineContext)
    private val addTemplateScreenViewModel = AddTemplateScreenViewModel(templateRepository, coroutineContext)
    private val templatesScreenViewModel = TemplatesScreenViewModel(templateRepository, logger, coroutineContext)
    private val provider = ViewModelProviderImpl(addTemplateScreenViewModel, templatesScreenViewModel, homeScreenViewModel)
    private val manager = DialogManagerImpl(provider, logger, coroutineContext)

    @Test
    fun `Dialog state is null when initialized`() {
        assertTrue { manager.dialogState.value == null }
    }

    @Test
    fun `When show is called, then state changes`() {
        assertStateTransition(
            stateFlow = manager.dialogState,
            assertState = { it is DialogState.AddFolder && it.viewModel === homeScreenViewModel },
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