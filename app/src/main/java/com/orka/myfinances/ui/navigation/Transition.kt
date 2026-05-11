package com.orka.myfinances.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation3.scene.Scene
import androidx.navigationevent.NavigationEvent

private const val NavigationAnimationDurationMillis = 500//280
private const val NavigationFadeDurationMillis = 380//220
private const val NavigationOffsetDivisor = 4
private const val NavigationPredictiveOffsetDivisor = 3

fun navigationForwardTransition():
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

fun navigationPopTransition():
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

fun navigationPredictivePopTransition():
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