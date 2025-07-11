package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.datasources.CategoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext

class HomeScreenViewModel(
    logger: Logger,
    private val dataSource: CategoryDataSource,
    context: CoroutineContext = Dispatchers.Main
) : ViewModel<HomeScreenState>(
    initialState = HomeScreenState.Initial,
    logger = logger,
    context = context
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        updateState { HomeScreenState.Loading }
        yield()
        val categories = dataSource.get()
        if(categories != null)
            updateState { HomeScreenState.Success(categories) }
        else
            updateState { HomeScreenState.Error("No categories found") }
    }
}