package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.User
import com.orka.myfinances.fixtures.DummyNavigationManager
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.home.parts.ProfileTopBar

@Composable
fun ProfileContent(
    modifier: Modifier,
    user: User,
    navigationManager: NavigationManager
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            painter = painterResource(R.drawable.account_circle_outlined),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )

        VerticalSpacer(8)
        Text(text = "ID: ${user.id.value}")
        Text(text = user.phone ?: stringResource(R.string.no_phone_number))

        VerticalSpacer(16)
        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp))
        
        VerticalSpacer(16)
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { navigationManager.navigateToSettings() }
                ) {
                    Text(text = stringResource(R.string.settings))
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { navigationManager.navigateToHistory() }
                ) {
                    Text(text = stringResource(R.string.history))
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { navigationManager.navigateToTemplates() }
                ) {
                    Text(text = stringResource(R.string.templates))
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { navigationManager.navigateToClients() }
                ) {
                    Text(text = stringResource(R.string.clients))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ProfileContentPreview() {
    val navItems = listOf(
        NavItem(
            index = 0,
            name = stringResource(R.string.home),
            iconRes = IconRes(
                selected = R.drawable.home_filled,
                unSelected = R.drawable.home_outlined
            )
        ),
        NavItem(
            index = 1,
            name = stringResource(R.string.basket),
            iconRes = IconRes(
                unSelected = R.drawable.shopping_cart_outlined,
                selected = R.drawable.shopping_cart_filled
            )
        ),
        NavItem(
            index = 2,
            name = stringResource(R.string.profile),
            iconRes = IconRes(
                unSelected = R.drawable.account_circle_outlined,
                selected = R.drawable.account_circle_filled
            )
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ProfileTopBar(user = user1)
        },
        bottomBar = {
            NavigationBar {
                navItems.forEach {
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(
                                painter = painterResource(it.iconRes.unSelected),
                                contentDescription = it.name
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        ProfileContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            user = user1,
            navigationManager = DummyNavigationManager()
        )
    }
}