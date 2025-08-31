package com.orka.myfinances.ui.parts

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.R
import com.orka.myfinances.ui.navigation.Destinations

@Composable
fun MainNavBar(
    modifier: Modifier = Modifier,
    backStack: SnapshotStateList<Destinations>
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = backStack.last() is Destinations.Home,
            icon = {
                val currentDestination = backStack.last()
                val iconRes = if (currentDestination is Destinations.Home)
                    R.drawable.home_filled
                else R.drawable.home_outlined
                Icon(painter = painterResource(id = iconRes), contentDescription = null)
            },
            onClick = {
                val currentDestination = backStack.last()
                if(currentDestination !is Destinations.Home) {
                    if(backStack.find { it is Destinations.Home } != null) {
                        backStack.removeAll { it !is Destinations.Home }
                    } else {
                        backStack.add(Destinations.Home(null))
                    }
                }
            },
        )

        NavigationBarItem(
            selected = backStack.last() is Destinations.Profile,
            icon = {
                val currentDestination = backStack.last()
                val iconRes = if (currentDestination is Destinations.Profile)
                    R.drawable.account_circle_filled
                else R.drawable.account_circle_outlined
                Icon(painter = painterResource(id = iconRes), contentDescription = null)
            },
            onClick = {
                val currentDestination = backStack.last()
                if(currentDestination !is Destinations.Profile) {
                    val index = backStack.indexOfFirst { it is Destinations.Profile }
                    if(index > 0) {
                        backStack.removeRange(index + 1, backStack.size - 1)
                    } else {
                        backStack.add(Destinations.Profile(null))
                    }
                }
            },
        )
    }
}