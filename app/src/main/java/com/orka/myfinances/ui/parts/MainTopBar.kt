package com.orka.myfinances.ui.parts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.navigation.Destinations
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    backStack: SnapshotStateList<Destinations>,
    dialogVisible: MutableState<Boolean>
) {
    val currentDestination = backStack.last()

    when (currentDestination) {
        is Destinations.Home -> {
            HomeScreenTopBar(modifier = modifier) { dialogVisible.value = true }
        }

        is Destinations.Category -> {

        }

        Destinations.Profile -> {

        }
    }
}