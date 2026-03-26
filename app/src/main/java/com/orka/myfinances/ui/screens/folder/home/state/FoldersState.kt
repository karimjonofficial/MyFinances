package com.orka.myfinances.ui.screens.folder.home.state

import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

sealed interface FoldersState {
    data object Initial : FoldersState
    data object Loading : FoldersState
    data class Success(val folders: List<FolderUiModel>) : FoldersState
    data object Error: FoldersState
}