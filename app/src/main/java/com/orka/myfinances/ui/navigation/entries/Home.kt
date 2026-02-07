package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreen

fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    session: Session,
    sessionManager: SessionManager,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    HomeScreen(
        modifier = modifier,
        factory = factory,
        sessionManager = sessionManager,
        session = session,
        selectFolder = { folder ->
            when (folder) {
                is Catalog -> navigator.navigateToCatalog(folder)
                is Category -> navigator.navigateToCategory(folder)
            }
        },
        navigator = navigator
    )
}