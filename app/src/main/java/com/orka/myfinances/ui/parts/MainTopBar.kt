package com.orka.myfinances.ui.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.ui.navigation.Destinations
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    backStack: List<Destinations>,
    dialogManager: DialogManager,
    session: Session
) {
    val currentDestination = backStack.last()

    when (currentDestination) {
        is Destinations.Home -> {
            HomeScreenTopBar(modifier = modifier) {
                val viewModel = currentDestination.viewModel
                val dialog = DialogState.AddFolder(viewModel)
                dialogManager.show(dialog)
            }
        }

        is Destinations.Profile -> {
            TopAppBar(
                modifier = modifier,
                title = { Text(session.user.firstName) }
            )
        }

        is Destinations.Folder -> {}
    }
}