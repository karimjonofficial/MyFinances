package com.orka.myfinances.ui.navigation

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
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.home.parts.AddProductDialog

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    dialogVisible: MutableState<Boolean>,
    backStack: SnapshotStateList<Destinations>,
    viewModel: HomeScreenViewModel
) {

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeRange(1, backStack.size - 1)
        },
        entryProvider = entryProvider {
            entry<Destinations.Home> {
                val uiState = viewModel.uiState.collectAsState()

                HomeScreen(
                    modifier = modifier,
                    state = uiState.value
                )

                if (dialogVisible.value) {
                    AddProductDialog(
                        dismissRequest = { dialogVisible.value = false },
                        onSuccess = {
                            viewModel.addCategory(it)
                            dialogVisible.value = false
                        },
                        onCancel = { dialogVisible.value = false }
                    )
                }
            }

            entry<Destinations.Category> { category ->
                val categoryNavBackStack = remember {
                    mutableStateListOf<CategoryDestinations>(CategoryDestinations.Warehouse)
                }

                NavDisplay(
                    backStack = categoryNavBackStack,
                    onBack = { categoryNavBackStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<CategoryDestinations.Warehouse> {

                        }

                        entry<CategoryDestinations.Products> {

                        }
                    }
                )
            }

            entry<Destinations.Profile> { profile ->

            }
        }
    )
}