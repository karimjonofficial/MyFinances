package com.orka.myfinances

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.factories.ViewModelProvider
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

class DialogManagerImpl(
    private val provider: ViewModelProvider,
    logger: Logger,
    defaultCoroutineContext: CoroutineContext = Dispatchers.Default,
) : ViewModel<DialogState?>(
    initialState = null,
    defaultCoroutineContext = defaultCoroutineContext,
    logger = logger
), DialogManager {
    override val dialogState: StateFlow<DialogState?> = state.asStateFlow()

    override fun addFolderDialog() {
        launch {
            val homeViewModel = provider.homeViewModel()
            val templatesViewModel = provider.templatesViewModel()
            state.update { DialogState.AddFolder(homeViewModel, templatesViewModel) }
        }
    }

    override fun hide() {
        launch { state.update { null } }
    }
}