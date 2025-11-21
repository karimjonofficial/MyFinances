package com.orka.myfinances.ui.parts

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.R
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager

@Composable
fun MainNavBar(
    modifier: Modifier = Modifier,
    destination: Destination,
    navigationManager: NavigationManager
) {
        NavigationBar(modifier = modifier) {

            NavigationBarItem(
                selected = destination is Destination.Home,
                icon = {
                    val iconRes = if (destination is Destination.Home)
                        R.drawable.home_filled
                    else R.drawable.home_outlined

                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null
                    )
                },
                onClick = {
                    if (destination !is Destination.Home)
                        navigationManager.navigateToHome()
                }
            )

            NavigationBarItem(
                selected = destination is Destination.Basket,
                icon = {
                    val iconRes = if (destination is Destination.Basket)
                        R.drawable.shopping_cart_filled
                    else R.drawable.shopping_cart_outlined

                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null
                    )
                },
                onClick = {
                    if (destination !is Destination.Basket)
                        navigationManager.navigateToBasket()
                }
            )

            NavigationBarItem(
                selected = destination is Destination.Profile,
                icon = {
                    val iconRes = if (destination is Destination.Profile)
                        R.drawable.account_circle_filled
                    else R.drawable.account_circle_outlined

                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null
                    )
                },
                onClick = {
                    if (destination !is Destination.Profile)
                        navigationManager.navigateToProfile()
                },
            )

            NavigationBarItem(
                selected = destination is Destination.Settings,
                icon = {
                    val iconRes = if (destination is Destination.Settings)
                        R.drawable.settings_filled
                    else R.drawable.settings_outlined

                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null
                    )
                },
                onClick = {
                    if (destination !is Destination.Settings)
                        navigationManager.navigateToSettings()
                }
            )
        }
}