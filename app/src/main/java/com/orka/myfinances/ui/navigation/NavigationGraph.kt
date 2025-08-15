package com.orka.myfinances.ui.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.home.parts.FoldersList

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    dialogVisible: MutableState<Boolean>,
    backStack: SnapshotStateList<Destinations>,
    viewModel: HomeScreenViewModel
) {

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeRange(1, backStack.size - 1) },
        transitionSpec = {
            ContentTransform(
                targetContentEnter = EnterTransition.None,
                initialContentExit = ExitTransition.None,
                targetContentZIndex = 0f,
                sizeTransform = SizeTransform()
            )
        },
        entryProvider = entryProvider {
            entry<Destinations.Home> {
                val uiState = viewModel.uiState.collectAsState()

                HomeScreen(
                    modifier = modifier,
                    state = uiState.value
                )

                if (dialogVisible.value) {
                    AddFolderDialog(
                        templates = emptyList(),
                        dismissRequest = { dialogVisible.value = false },
                        onSuccess = { name, type ->
                            viewModel.addFolder(name, type)
                            dialogVisible.value = false
                        },
                        onCancel = { dialogVisible.value = false }
                    )
                }
            }
            entry<Destinations.Folder> { destination ->
                val folder = destination.folder
                when (folder) {
                    is Catalog -> {
                        if (folder.folders != null) {
                            FoldersList(
                                modifier = modifier,
                                items = folder.folders
                            )
                        } else {
                            //TODO error
                        }
                    }

                    is ProductFolder -> {

                    }
                }
            }
            entry<Destinations.Profile> { profile ->

            }
        }
    )
}