package com.orka.myfinances

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.main.NavigationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

class NavigationManagerImpl(
    private val homeScreenViewModel: HomeScreenViewModel,
    logger: Logger,
    defaultCoroutineContext: CoroutineContext = Dispatchers.Default
) : ViewModel<List<Destination>>(
    initialState = listOf(Destination.Home(homeScreenViewModel)),
    defaultCoroutineContext = defaultCoroutineContext,
    logger = logger
), NavigationManager {
    override val backStack = state.asStateFlow()

    override fun navigateToHome() {
        val list = createBackStack()
        state.update { list }
    }

    override fun navigateToProfile() {
        if (state.value.last() !is Destination.Profile) {
            state.update { createBackStack(Destination.Profile) }
        }
    }

    override fun navigateToFolder(folder: Folder) {
        val last = state.value.last()
        if (!(last is Destination.Folder && last.folder == folder)) {
            val backstack = state.value
            val new = backstack + Destination.Folder(folder)
            state.update { new }
        }
    }

    override fun back() {
        val backstack = state.value
        if (backstack.size > 1) {
            val list = backstack.dropLast(1)
            state.update { list }
        }
    }

    private fun createBackStack(vararg destinations: Destination = emptyArray()): List<Destination> {
        val list = listOf(Destination.Home(homeScreenViewModel)) + destinations
        return list
    }
}