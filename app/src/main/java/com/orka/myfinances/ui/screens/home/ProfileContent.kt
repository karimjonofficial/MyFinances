package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.home.models.ProfileOption
import com.orka.myfinances.ui.screens.home.parts.ProfileTopBar
import com.orka.myfinances.ui.screens.home.viewmodel.profile.ProfileContentModel
import com.orka.myfinances.ui.screens.home.viewmodel.profile.ProfileInteractor
import com.orka.myfinances.ui.theme.MyFinancesTheme

// Maps each option index to a drawable icon resource
private fun iconForOption(index: Int): Int = when (index) {
    0 -> R.drawable.settings_filled
    1 -> R.drawable.date_range
    2 -> R.drawable.article
    3 -> R.drawable.person
    4 -> R.drawable.receipt_long
    5 -> R.drawable.attach_money
    else -> R.drawable.arrow_right
}

@Suppress("UNCHECKED_CAST")
@Composable
fun ProfileContent(
    modifier: Modifier,
    state: State,
    interactor: ProfileInteractor,
    session: Session,
    sessionManager: SessionManager//TODO remove it to viewmodel
) {
    val options = listOf(
        ProfileOption(
            index = 0,
            name = stringResource(R.string.settings),
            action = interactor::settings
        ),
        ProfileOption(
            index = 1,
            name = stringResource(R.string.history),
            action = interactor::history
        ),
        ProfileOption(
            index = 2,
            name = stringResource(R.string.templates),
            action = interactor::templates
        ),
        ProfileOption(
            index = 3,
            name = stringResource(R.string.clients),
            action = interactor::clients
        ),
        ProfileOption(
            index = 4,
            name = stringResource(R.string.orders),
            action = interactor::orders
        ),
        ProfileOption(
            index = 5,
            name = stringResource(R.string.debts),
            action = interactor::debts
        )
    )
    val exposed = rememberSaveable { mutableStateOf(false) }

    val user   = (state as? State.Success<*>)?.let { (it.value as ProfileContentModel).user }
    val offices = (state as? State.Success<*>)?.let { (it.value as ProfileContentModel).offices } ?: emptyList()
    val displayName = if (user != null) {
        listOfNotNull(user.firstName, user.lastName).joinToString(" ")
    } else stringResource(R.string.loading)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ProfileTopBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
        // ── Profile header card ─────────────────────────────────────────
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(R.drawable.account_circle_outlined),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null
                        )
                    }

                    VerticalSpacer(12)
                    Text(
                        text = displayName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    if (user?.phone != null) {
                        VerticalSpacer(4)
                        Text(
                            text = user.phone,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }

                    VerticalSpacer(16)
                    OutlinedExposedDropDownTextField(
                        text = when (state) {
                            is State.Initial  -> stringResource(R.string.loading)
                            is State.Success<*> -> session.office.name
                            is State.Failure  -> state.error.str()
                            is State.Loading  -> state.message.str()
                        },
                        label = stringResource(R.string.select_your_office),
                        menuExpanded = exposed.value,
                        onExpandChange = { exposed.value = it },
                        onDismissRequested = { exposed.value = false },
                        items = offices,
                        itemText = { it.name },
                        onItemSelected = { office ->
                            sessionManager.setOffice(session.credentials, office)
                            exposed.value = false
                        }
                    )
                }
            }
        }

        // ── Section label ───────────────────────────────────────────────
        item {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                text = stringResource(R.string.more),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }

        // ── Navigation items as list rows ───────────────────────────────
        items(items = options) { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { option.action() }
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(iconForOption(option.index)),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = option.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (option.index < options.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(start = 60.dp, end = 20.dp)
                )
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


