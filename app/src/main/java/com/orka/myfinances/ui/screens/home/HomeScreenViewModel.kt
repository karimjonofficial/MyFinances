package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.folder.FolderType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel(
    private val repository: FolderRepository,
    logger: Logger,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : ViewModel<HomeScreenState>(
    initialState = HomeScreenState.Initial,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        setState(HomeScreenState.Loading)
        val folders = repository.get()
        if(folders != null)
            setState(HomeScreenState.Success(folders))
        else setState(HomeScreenState.Error)
    }

    fun addFolder(name: String, type: FolderType) = launch {
        val previousState = state.value
        setState(HomeScreenState.Loading)
        val request = AddFolderRequest(name, type)
        val folder = repository.add(request)
        if(folder != null) {
            val folders = repository.get()
            setState(HomeScreenState.Success(folders!!))
        }
        else setState(previousState)
    }
}