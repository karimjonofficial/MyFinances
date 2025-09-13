package com.orka.myfinances.ui.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.home.parts.FoldersList
import com.orka.myfinances.ui.screens.home.parts.WarehouseGrid
import com.orka.myfinances.ui.screens.main.NavigationManager

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    dialogState: DialogState?,
    backStack: List<Destination>,
    navigationManager: NavigationManager,
    dialogManager: DialogManager
) {
    NavDisplay(
        backStack = backStack,
        onBack = { navigationManager.back() },
        transitionSpec = {
            ContentTransform(
                targetContentEnter = EnterTransition.None,
                initialContentExit = ExitTransition.None,
                targetContentZIndex = 0f,
                sizeTransform = SizeTransform()
            )
        },
        entryProvider = entryProvider {
            entry<Destination.Home> { destination ->
                val uiState = destination.viewModel.uiState.collectAsState()

                HomeScreen(
                    modifier = modifier,
                    state = uiState.value
                ) {
                    navigationManager.navigateToFolder(it)
                }
            }

            entry<Destination.Folder> { destination ->
                val folder = destination.folder

                when (folder) {
                    is Catalog -> {
                        FoldersList(
                            modifier = modifier,
                            items = folder.folders
                        ) {
                            navigationManager.navigateToFolder(it)
                        }
                    }

                    is ProductFolder -> {
                        WarehouseGrid(
                            modifier = modifier,
                            products = folder.products,
                            stockItems = folder.stockItems
                        )
                    }
                }
            }

            entry<Destination.Profile> { profile ->

            }

            entry<Destination.Notifications> {
                
            }
        }
    )
    if (dialogState is DialogState.AddFolder) {
        AddFolderDialog(
            templates = emptyList(),
            dismissRequest = { dialogManager.hide() },
            onSuccess = { name, type ->
                dialogState.viewModel.addFolder(name, type)
                dialogManager.hide()
            },
            onCancel = { dialogManager.hide() }
        )
    }
}

