package com.orka.myfinances

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

class DialogManagerImpl(
    defaultCoroutineContext: CoroutineContext = Dispatchers.Default,
    logger: Logger
) : ViewModel<DialogState?>(
    initialState = null,
    defaultCoroutineContext = defaultCoroutineContext,
    logger = logger
), DialogManager {
    override val dialogState: StateFlow<DialogState?> = state.asStateFlow()

    override fun show(dialog: DialogState) {
        //TODO the problem with concurrent state changes is not solved yet
        launch { state.update { dialog } }
    }

    override fun hide() {
        launch { state.update { null } }
    }
}