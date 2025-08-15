package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.AddFolderRequest
import com.orka.myfinances.data.repositories.FolderRepository
import com.orka.myfinances.data.repositories.FolderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class HomeScreenViewModel(
    logger: Logger,
    private val repository: FolderRepository,
    context: CoroutineContext = Dispatchers.Main
) : ViewModel<HomeScreenState>(
    initialState = HomeScreenState.Initial,
    logger = logger,
    defaultCoroutineContext = context
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        setStateAsync(HomeScreenState.Loading)
        val folders = repository.get()
        if(folders != null)
            setState(HomeScreenState.Success(folders))
        else setState(HomeScreenState.Error)
    }

    fun addFolder(name: String, type: FolderType) = launch {
        val previousState = state.value
        setStateAsync(HomeScreenState.Loading)
        val request = AddFolderRequest(name, type)
        val folder = repository.add(request)
        if(folder != null)
            setState(HomeScreenState.Success(listOf(folder)))
        else setStateAsync(previousState)
    }
}