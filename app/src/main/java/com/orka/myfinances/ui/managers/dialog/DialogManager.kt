package com.orka.myfinances.ui.managers.dialog

import kotlinx.coroutines.flow.StateFlow

interface DialogManager {
    val dialogState: StateFlow<DialogState?>
    fun show(dialog: DialogState)
    fun hide()
}