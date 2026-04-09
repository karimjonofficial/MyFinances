package com.orka.myfinances.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.offices
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.components.FooterSpacer
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.profile.components.OptionButton
import com.orka.myfinances.ui.screens.profile.components.UserIcon
import com.orka.myfinances.ui.screens.profile.models.ProfileContentModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun ProfileContent(
    modifier: Modifier,
    state: State<ProfileContentModel>,
    interactor: ProfileInteractor,
) {
    val exposed = rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            UserIcon(Modifier.size(160.dp))

            VerticalSpacer(8)
            NameText(state = state)
            PhoneText(state = state)

            OutlinedExposedDropDownTextField(
                text = when (state) {
                    is State.Success -> state.value.officeName
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
                itemText = { it.title },
                onItemSelected = { office ->
                    interactor.setOffice(office)
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
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                options(interactor).forEach {
                    OptionButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(it.titleRes),
                        painter = painterResource(it.iconRes),
                        action = it.action
                    )
                }
            }

            FooterSpacer()
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
                state = State.Success(
                    ProfileContentModel(
                        offices = offices.map { it.toItemModel() },
                        officeName = office1.name,
                        name = "${user1.firstName} ${user1.lastName}",
                        phone = user1.phone
                    )
                ),
                interactor = ProfileInteractor.dummy
            )
        }
    }
}