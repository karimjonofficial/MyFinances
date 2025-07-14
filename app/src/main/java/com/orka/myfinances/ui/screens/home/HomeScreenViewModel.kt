package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.datasources.CategoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class HomeScreenViewModel(
    logger: Logger,
    private val dataSource: CategoryDataSource,
    context: CoroutineContext = Dispatchers.Main
) : ViewModel<HomeScreenState>(
    initialState = HomeScreenState.Initial,
    logger = logger,
    defaultCoroutineContext = context
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        setState(HomeScreenState.Loading)
        val categories = dataSource.get()
        if(categories != null) setState(HomeScreenState.Success(categories))
        else setState(HomeScreenState.Error("No categories found"))
    }

    fun addCategory(name: String) = launch {
        val previousState = state.value
        setStateAsync(HomeScreenState.Loading)
        setStateAsync(previousState)
    }
}