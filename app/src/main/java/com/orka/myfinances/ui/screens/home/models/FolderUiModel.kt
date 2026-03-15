package com.orka.myfinances.ui.screens.home.models

import com.orka.myfinances.data.models.Id

data class FolderUiModel(
    val model: FolderButtonModel,
    val id: Id,
    val isCatalog: Boolean
)