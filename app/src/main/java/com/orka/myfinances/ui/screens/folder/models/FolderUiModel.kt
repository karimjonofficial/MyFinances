package com.orka.myfinances.ui.screens.folder.models

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.folder.components.FolderButtonModel

data class FolderUiModel(
    val model: FolderButtonModel,
    val id: Id,
    val isCatalog: Boolean
)