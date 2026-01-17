package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.viewmodel.FoldersContentViewModel

fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    user: User,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {
    HomeScreen(
        modifier = modifier,
        user = user,
        foldersViewModel = destination.foldersViewModel as FoldersContentViewModel,
        basketViewModel = destination.basketViewModel as BasketContentViewModel,
        selectFolder = { folder ->
            when(folder) {
                is Catalog -> navigationManager.navigateToCatalog(folder)
                is Category -> navigationManager.navigateToWarehouse(folder)
            }
        },
        navigationManager = navigationManager
    )
}