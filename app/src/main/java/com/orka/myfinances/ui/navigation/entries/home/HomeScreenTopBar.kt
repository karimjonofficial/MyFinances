package com.orka.myfinances.ui.navigation.entries.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.orka.myfinances.application.viewmodels.basket.BasketContentViewModel
import com.orka.myfinances.application.viewmodels.folder.home.FoldersContentViewModel
import com.orka.myfinances.application.viewmodels.profile.ProfileContentViewModel
import com.orka.myfinances.ui.screens.basket.BasketScreenTopBar
import com.orka.myfinances.ui.screens.folder.home.parts.FoldersContentTopBar
import com.orka.myfinances.ui.screens.profile.ProfileTopBar

@Composable
fun HomeScreenTopBar(
    index: Int,
    onAddFolder: () -> Unit,
    foldersViewModel: FoldersContentViewModel,
    basketViewModel: BasketContentViewModel,
    profileViewModel: ProfileContentViewModel
) {
    when (index) {
        0 -> {
            FoldersContentTopBar(
                onAddClick = onAddFolder,
                onNotificationsClick = foldersViewModel::navigateToNotifications,
                onSearchClick = foldersViewModel::navigateToSearch
            )
        }

        1 -> {
            val basketState = basketViewModel.uiState.collectAsState()

            BasketScreenTopBar(
                state = basketState.value,
                interactor = basketViewModel
            )
        }

        2 -> ProfileTopBar(interactor = profileViewModel)
    }
}