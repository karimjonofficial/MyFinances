package com.orka.myfinances.ui.screens.home.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.folder.FolderType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow

class FoldersContentViewModel(
    private val repository: FolderRepository,
    logger: Logger,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : ViewModel<FoldersState>(
    initialState = FoldersState.Initial,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        setState(FoldersState.Loading)
        val folders = repository.get()
        if(folders != null)
            setState(FoldersState.Success(folders))
        else setState(FoldersState.Error)
    }

    fun addFolder(name: String, type: FolderType) = launch {
        val previousState = state.value
        setState(FoldersState.Loading)
        val request = AddFolderRequest(name, type)
        val folder = repository.add(request)
        if(folder != null) {
            val folders = repository.get()
            setState(FoldersState.Success(folders!!))
        }
        else setState(previousState)
    }
}