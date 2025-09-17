package com.orka.myfinances.ui.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.parts.MainDialog
import com.orka.myfinances.ui.managers.navigation.NavigationManager

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
        entryProvider = { destination ->
            entryProvider(modifier, destination, navigationManager)
        }
    )
    if(dialogState != null) {
        MainDialog(dialogState, dialogManager, navigationManager)
    }
}