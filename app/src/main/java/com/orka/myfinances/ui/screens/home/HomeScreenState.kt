package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.data.models.folder.Folder

sealed interface HomeScreenState {
    data object Initial : HomeScreenState
    data object Loading : HomeScreenState
    data class Success(val folders: List<Folder>) : HomeScreenState
    data object Error: HomeScreenState
}