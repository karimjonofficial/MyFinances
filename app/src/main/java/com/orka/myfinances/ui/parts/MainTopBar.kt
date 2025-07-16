package com.orka.myfinances.ui.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.orka.myfinances.models.Session
import com.orka.myfinances.ui.navigation.Destinations
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    backStack: SnapshotStateList<Destinations>,
    session: Session
) {
    val currentDestination = backStack.last()

    when (currentDestination) {
        is Destinations.Home -> {
            HomeScreenTopBar(modifier = modifier) {  }
        }

        Destinations.Profile -> {
            TopAppBar(
                title = { Text(session.credential.id.toString()) }
            )
        }

        is Destinations.Category -> {}
    }
}