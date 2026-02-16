package com.orka.myfinances.ui.screens.home.models

import com.orka.myfinances.data.models.folder.Folder

data class FolderUiModel(
    val model: FolderButtonModel,
    val folder: Folder
)