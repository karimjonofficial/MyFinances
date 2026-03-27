package com.orka.myfinances.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.fixtures.managers.DummySessionManager
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.session
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun ProfileContent(
    modifier: Modifier,
    state: State<ProfileContentModel>,
    interactor: ProfileInteractor,
    session: Session,
    sessionManager: SessionManager//TODO remove it to viewmodel
) {
    val exposed = rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
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
            Text(
                text = "ID: ${
                    if (state is State.Success) state.value.user.id.value else stringResource(
                        R.string.loading
                    )
                }"
            )
            Text(
                text = if (state is State.Success) state.value.user.phone
                    ?: stringResource(R.string.no_phone_number) else stringResource(R.string.loading)
            )

            OutlinedExposedDropDownTextField(
                text = when (state) {
                    is State.Success -> session.office.name
                    is State.Failure -> state.error.str()
                    is State.Loading -> state.message.str()
                },
                label = "",
                menuExpanded = exposed.value,
                onExpandChange = { exposed.value = it },
                onDismissRequested = { exposed.value = false },
                items = when (state) {
                    is State.Success -> state.value.offices
                    else -> emptyList()
                },
                itemText = { it.name },
                onItemSelected = { office ->
                    sessionManager.setOffice(session.credentials, office)
                    exposed.value = false
                }
            )

            VerticalSpacer(16)
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            VerticalSpacer(16)
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                options(interactor).forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { it.action() }
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(it.iconRes),
                            contentDescription = stringResource(it.titleRes)
                        )

                        Text(
                            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                            text = stringResource(it.titleRes),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold
                        )

                        Icon(
                            painter = painterResource(R.drawable.arrow_right),
                            contentDescription = null
                        )
                    }
                }
            }
        }

        item {
            VerticalSpacer(8)
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

    MyFinancesTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { ProfileTopBar() },
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
                state = State.Success(ProfileContentModel(listOf(office1), user1)),
                interactor = ProfileInteractor.dummy,
                sessionManager = DummySessionManager()
            )
        }
    }
}