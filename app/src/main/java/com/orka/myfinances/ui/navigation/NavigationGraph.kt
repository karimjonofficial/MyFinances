package com.orka.myfinances.ui.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.data.models.User
import com.orka.myfinances.impl.ui.managers.NavigationManagerImpl
import com.orka.myfinances.ui.managers.navigation.Destination

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    user: User,
    backStack: List<Destination>,
    navigationManager: NavigationManagerImpl
) {
    NavDisplay(
        modifier = modifier,
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
            entryProvider(Modifier.fillMaxSize(), user, destination, navigationManager)
        }
    )
}