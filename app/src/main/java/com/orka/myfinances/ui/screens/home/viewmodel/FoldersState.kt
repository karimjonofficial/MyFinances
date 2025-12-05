package com.orka.myfinances.ui.screens.home.viewmodel

import com.orka.myfinances.data.models.folder.Folder

sealed interface FoldersState {
    data object Initial : FoldersState
    data object Loading : FoldersState
    data class Success(val folders: List<Folder>) : FoldersState
    data object Error: FoldersState
}