package com.orka.myfinances.ui.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState.AddFolder
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar
import com.orka.myfinances.ui.managers.navigation.NavigationManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    dialogManager: DialogManager,
    navigationManager: NavigationManager,
    session: Session
) {

    when (currentDestination) {
        is Destination.Home -> {
            HomeScreenTopBar(
                modifier = modifier,
                onAddClick = {
                    val dialog = AddFolder(currentDestination.viewModel)
                    dialogManager.show(dialog)
                },
                onNotificationsClick = {
                    navigationManager.navigateToNotifications()
                },
                onSearchClick = {

                }
            )
        }

        is Destination.Profile -> {
            TopAppBar(
                modifier = modifier,
                title = { Text(session.user.firstName) }
            )
        }

        is Destination.Catalog -> {
            when(currentDestination.catalog) {

                is Catalog -> {
                    TopAppBar(
                        modifier = modifier,
                        title = { Text(text = stringResource(R.string.catalog)) }
                    )
                }

                is ProductFolder -> {
                    TopAppBar(
                        modifier = modifier,
                        title = { Text(text = stringResource(R.string.products)) }
                    )
                }
            }
        }

        Destination.Notifications -> {
            TopAppBar(
                modifier = modifier,
                title = { Text(text = stringResource(R.string.notifications)) }
            )
        }

        is Destination.AddTemplate -> {
            TopAppBar(
                modifier = modifier,
                title = { Text(text = stringResource(R.string.templates)) }
            )
        }
    }
}