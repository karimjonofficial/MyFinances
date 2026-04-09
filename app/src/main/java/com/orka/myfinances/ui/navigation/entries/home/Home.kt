package com.orka.myfinances.ui.navigation.entries.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreen

fun homeEntry(
    modifier: Modifier,
    destination: Destination.Home,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val officeId = session.officeId.value.toString()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    val foldersViewModel = viewModel(key = "folders_$officeId") {
        factory.foldersViewModel()
    }
    val basketViewModel = viewModel(key = "basket_$officeId") {
        factory.basketViewModel()
    }
    val profileViewModel = viewModel(key = "profile_$officeId") {
        factory.profileViewModel()
    }
    val sheetViewModel = viewModel(key = "sheet_$officeId") {
        factory.templateBottomSheetViewModel()
    }

    HomeScreen(
        modifier = modifier,
        topBar = {
            HomeScreenTopBar(
                index = it,
                onAddFolder = { dialogVisible.value = true },
                foldersViewModel = foldersViewModel,
                basketViewModel = basketViewModel
            )
        },
        content = { contentModifier, index ->
            HomeScreenContent(
                modifier = contentModifier,
                index = index,
                officeId = officeId,
                foldersViewModel = foldersViewModel,
                dialogVisible = dialogVisible,
                basketViewModel = basketViewModel,
                profileViewModel = profileViewModel,
                sheetViewModel = sheetViewModel
            )
        }
    )
}