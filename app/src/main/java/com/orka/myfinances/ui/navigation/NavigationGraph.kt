package com.orka.myfinances.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    session: Session,
    backStack: List<Destination>,
    navigator: Navigator,
    factory: Factory
) {
    key(session.officeId.value) {
        NavDisplay(
            modifier = modifier.background(MaterialTheme.colorScheme.background),
            backStack = backStack,
            onBack = { navigator.back() },
            transitionSpec = navigationForwardTransition(),
            popTransitionSpec = navigationPopTransition(),
            predictivePopTransitionSpec = navigationPredictivePopTransition(),
            entryProvider = { destination ->
                entryProvider(
                    modifier = Modifier.fillMaxSize(),
                    session = session,
                    destination = destination,
                    factory = factory
                )
            }
        )
    }
}

private const val NavigationAnimationDurationMillis = 280
private const val NavigationFadeDurationMillis = 220
private const val NavigationOffsetDivisor = 4
private const val NavigationPredictiveOffsetDivisor = 3

private fun navigationForwardTransition():
    AnimatedContentTransitionScope<Scene<Destination>>.() -> ContentTransform = {
    ContentTransform(
        targetContentEnter =
            slideInHorizontally(
                animationSpec = tween(NavigationAnimationDurationMillis),
                initialOffsetX = { fullWidth -> fullWidth / NavigationOffsetDivisor },
            ) + fadeIn(animationSpec = tween(NavigationFadeDurationMillis)),
        initialContentExit =
            slideOutHorizontally(
                animationSpec = tween(NavigationAnimationDurationMillis),
                targetOffsetX = { fullWidth -> -fullWidth / NavigationOffsetDivisor },
            ) + fadeOut(animationSpec = tween(NavigationFadeDurationMillis)),
    )
}

private fun navigationPopTransition():
    AnimatedContentTransitionScope<Scene<Destination>>.() -> ContentTransform = {
    ContentTransform(
        targetContentEnter =
            slideInHorizontally(
                animationSpec = tween(NavigationAnimationDurationMillis),
                initialOffsetX = { fullWidth -> -fullWidth / NavigationOffsetDivisor },
            ) + fadeIn(animationSpec = tween(NavigationFadeDurationMillis)),
        initialContentExit =
            slideOutHorizontally(
                animationSpec = tween(NavigationAnimationDurationMillis),
                targetOffsetX = { fullWidth -> fullWidth / NavigationOffsetDivisor },
            ) + fadeOut(animationSpec = tween(NavigationFadeDurationMillis)),
    )
}

private fun navigationPredictivePopTransition():
    AnimatedContentTransitionScope<Scene<Destination>>.(
        @NavigationEvent.SwipeEdge Int
    ) -> ContentTransform = { swipeEdge ->
    val targetOffsetSign =
        when (swipeEdge) {
            NavigationEvent.EDGE_RIGHT -> 1
            else -> -1
        }
    val exitOffsetSign = -targetOffsetSign

    ContentTransform(
        targetContentEnter =
            slideInHorizontally(
                animationSpec = tween(NavigationAnimationDurationMillis),
                initialOffsetX = { fullWidth ->
                    (fullWidth / NavigationPredictiveOffsetDivisor) * targetOffsetSign
                },
            ) + fadeIn(animationSpec = tween(NavigationFadeDurationMillis)),
        initialContentExit =
            slideOutHorizontally(
                animationSpec = tween(NavigationAnimationDurationMillis),
                targetOffsetX = { fullWidth ->
                    (fullWidth / NavigationOffsetDivisor) * exitOffsetSign
                },
            ) + fadeOut(animationSpec = tween(NavigationFadeDurationMillis)),
    )
}
