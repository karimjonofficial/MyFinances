package com.orka.myfinances.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.orka.myfinances.R
import com.orka.myfinances.application.manager.NavigationManager
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.managers.SessionManager

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager,
    factory: Factory,
    session: Session,
    sessionManager: SessionManager
) {
    val backStack by navigationManager.backStack.collectAsState()
    val currentDestination = backStack.lastOrNull()

    val isTopLevel = currentDestination is Destination.Home ||
            currentDestination is Destination.Basket ||
            currentDestination is Destination.Profile ||
            currentDestination is Destination.Settings

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = isTopLevel,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                MyFinancesBottomNavigation(
                    currentDestination = currentDestination,
                    navigator = navigationManager
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
            NavigationGraph(
                backStack = backStack,
                navigator = navigationManager,
                session = session,
                sessionManager = sessionManager,
                factory = factory
            )
        }
    }
}

@Composable
private fun MyFinancesBottomNavigation(
    currentDestination: Destination?,
    navigator: Navigator
) {
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentDestination is Destination.Home,
            onClick = { navigator.navigateToHome() },
            icon = { Icon(painterResource(if (currentDestination is Destination.Home) R.drawable.home_filled else R.drawable.home_outlined), contentDescription = stringResource(R.string.home)) },
            label = { Text(stringResource(R.string.home), fontWeight = FontWeight.SemiBold) },
            colors = colors
        )
        
        NavigationBarItem(
            selected = currentDestination is Destination.Basket,
            onClick = { navigator.navigateToBasket() },
            icon = { Icon(painterResource(if (currentDestination is Destination.Basket) R.drawable.shopping_cart_filled else R.drawable.shopping_cart_outlined), contentDescription = stringResource(R.string.basket)) },
            label = { Text(stringResource(R.string.basket), fontWeight = FontWeight.SemiBold) },
            colors = colors
        )
        
        NavigationBarItem(
            selected = currentDestination is Destination.Profile,
            onClick = { navigator.navigateToProfile() },
            icon = { Icon(painterResource(if (currentDestination is Destination.Profile) R.drawable.account_circle_filled else R.drawable.account_circle_outlined), contentDescription = stringResource(R.string.profile)) },
            label = { Text(stringResource(R.string.profile), fontWeight = FontWeight.SemiBold) },
            colors = colors
        )
        
        NavigationBarItem(
            selected = currentDestination is Destination.Settings,
            onClick = { navigator.navigateToSettings() },
            icon = { Icon(painterResource(R.drawable.settings_filled), contentDescription = stringResource(R.string.settings)) },
            label = { Text(stringResource(R.string.settings), fontWeight = FontWeight.SemiBold) },
            colors = colors
        )
    }
}