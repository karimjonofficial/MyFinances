package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.managers.DummySessionManager
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.session
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.home.parts.ProfileTopBar

@Composable
fun ProfileContent(
    modifier: Modifier,
    state: State,
    session: Session,
    navigator: Navigator,
    sessionManager: SessionManager
) {
    val options = listOf(
        ProfileOption(
            index = 0,
            name = stringResource(R.string.settings),
            action = { navigator.navigateToSettings() }
        ),
        ProfileOption(
            index = 1,
            name = stringResource(R.string.history),
            action = { navigator.navigateToHistory() }
        ),
        ProfileOption(
            index = 2,
            name = stringResource(R.string.templates),
            action = { navigator.navigateToTemplates() }
        ),
        ProfileOption(
            index = 3,
            name = stringResource(R.string.clients),
            action = { navigator.navigateToClients() }
        ),
        ProfileOption(
            index = 4,
            name = stringResource(R.string.orders),
            action = { navigator.navigateToOrders() }
        ),
        ProfileOption(
            index = 5,
            name = stringResource(R.string.debts),
            action = { navigator.navigateToDebts() }
        )
    )
    val exposed = rememberSaveable { mutableStateOf(false) }

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
        Text(text = "ID: ${session.user.id.value}")
        Text(text = session.user.phone ?: stringResource(R.string.no_phone_number))

        OutlinedExposedDropDownTextField(
            text = when (state) {
                State.Initial -> stringResource(R.string.loading)
                is State.Success<*> -> session.office.name
                is State.Failure -> state.error.str()
                is State.Loading -> state.message.str()
            },
            label = "",
            menuExpanded = exposed.value,
            onExpandChange = { exposed.value = it },
            onDismissRequested = { exposed.value = false },
            items = when (state) {
                is State.Success<*> -> state.value as List<Office>
                else -> emptyList()
            },
            itemText = { it.name },
            onItemSelected = { office ->
                sessionManager.setOffice(session.credential, office)
                exposed.value = false
            }
        )

        VerticalSpacer(16)
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        VerticalSpacer(16)
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 8.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = options) {
                Button(onClick = it.action) {
                    Text(text = it.name)
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
            session = session,
            navigator = DummyNavigator(),
            state = State.Success(listOf(office1)),
            sessionManager = DummySessionManager()
        )
    }
}