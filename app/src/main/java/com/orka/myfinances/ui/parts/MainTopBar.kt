package com.orka.myfinances.ui.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar
import com.orka.myfinances.ui.screens.warehouse.WarehouseScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    destination: Destination,
    dialogManager: DialogManager,
    navigationManager: NavigationManager,
    session: Session
) {

    when (destination) {
        is Destination.Home -> {
            HomeScreenTopBar(
                modifier = modifier,
                onAddClick = { dialogManager.addFolderDialog() },
                onNotificationsClick = { navigationManager.navigateToNotifications() },
                onSearchClick = {
                    //TODO implement something
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
            TopAppBar(
                modifier = modifier,
                title = { Text(text = destination.catalog.name) }
            )
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

        is Destination.Warehouse -> {
            WarehouseScreenTopBar(
                modifier = modifier,
                warehouse = destination.warehouse,
                onAddProductClick = { navigationManager.navigateToAddProduct(it) },
                onAddStockItemClick = { /**navigationManager.navigateToAddStockItem(it)**/ }
            )
        }

        Destination.Settings -> {
            TopAppBar(
                modifier = modifier,
                title = { Text(text = stringResource(R.string.settings)) }
            )
        }

        is Destination.Templates -> {
            TopAppBar(
                modifier = modifier,
                title = { Text(text = stringResource(R.string.templates)) }
            )
        }

        is Destination.AddProduct -> {
            TopAppBar(
                modifier = modifier,
                title = { Text(text = stringResource(R.string.add_product)) }
            )
        }
    }
}