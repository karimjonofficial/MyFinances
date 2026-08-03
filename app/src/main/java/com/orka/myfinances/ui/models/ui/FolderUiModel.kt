package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.button.FolderButtonModel

data class FolderUiModel(
    val model: FolderButtonModel,
    val id: Id,
    val isCatalog: Boolean
)