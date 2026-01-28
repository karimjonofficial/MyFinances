package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.home.HomeScreen

fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    user: User,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    HomeScreen(
        modifier = modifier,
        user = user,
        foldersViewModel = viewModel { factory.foldersViewModel() },
        basketViewModel = viewModel { factory.basketViewModel() },
        selectFolder = { folder ->
            when(folder) {
                is Catalog -> navigator.navigateToCatalog(folder)
                is Category -> navigator.navigateToCategory(folder)
            }
        },
        navigator = navigator
    )
}