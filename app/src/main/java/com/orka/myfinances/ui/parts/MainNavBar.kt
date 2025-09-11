package com.orka.myfinances.ui.parts

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.R
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.main.NavigationManager

@Composable
fun MainNavBar(
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager
) {
    NavigationBar(modifier = modifier) {
        val backStack = navigationManager.backStack.collectAsState()

        NavigationBarItem(
            selected = backStack.value.last() is Destination.Home,
            icon = {
                val currentDestination = backStack.value.last()
                val iconRes = if (currentDestination is Destination.Home)
                    R.drawable.home_filled
                else R.drawable.home_outlined
                Icon(painter = painterResource(id = iconRes), contentDescription = null)
            },
            onClick = {
                val currentDestination = backStack.value.last()
                if(currentDestination !is Destination.Home) {
                    navigationManager.navigateToHome()
                }
            }
        )

        NavigationBarItem(
            selected = backStack.value.last() is Destination.Profile,
            icon = {
                val currentDestination = backStack.value.last()
                val iconRes = if (currentDestination is Destination.Profile)
                    R.drawable.account_circle_filled
                else R.drawable.account_circle_outlined
                Icon(painter = painterResource(id = iconRes), contentDescription = null)
            },
            onClick = {
                val currentDestination = backStack.value.last()
                if(currentDestination !is Destination.Profile) {
                    navigationManager.navigateToProfile()
                }
            },
        )
    }
}