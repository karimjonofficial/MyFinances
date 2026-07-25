package com.orka.myfinances.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.branch1
import com.orka.myfinances.fixtures.resources.models.branches
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.ui.components.SelectionBottomSheet
import com.orka.myfinances.lib.ui.components.spacer.FooterSpacer
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.extensions.str
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.viewmodel.State
import com.orka.myfinances.ui.screens.profile.components.ProfileOptionButton
import com.orka.myfinances.ui.screens.profile.components.UserIcon
import com.orka.myfinances.ui.screens.profile.models.ProfileContentModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier,
    state: State<ProfileContentModel>,
    interactor: ProfileInteractor,
) {
    val isSheetVisible = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)

    if (isSheetVisible.value) {
        val branches = (state.value?.branches ?: emptyList())
            .groupBy { it.title.take(1).uppercase() }

        SelectionBottomSheet(
            onDismissRequest = { isSheetVisible.value = false },
            sheetState = sheetState,
            items = branches,
            selectedItem = (state as? State.Success)?.value?.let { model ->
                model.branches.find { it.title == model.branchName }
            },
            onSelected = { branch ->
                interactor.setBranch(branch)
                isSheetVisible.value = false
            },
            onSearch = { /* TODO: Local search if needed */ }
        )
    }

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val branchTitle = when(state) {
                    is State.Success -> state.value.branchName
                    is State.Failure -> state.error.str()
                    is State.Loading -> state.message.str()
                }

                VerticalSpacer(16)
                UserIcon(Modifier.size(160.dp))

                VerticalSpacer(8)
                NameText(state = state)
                PhoneText(state = state)

                VerticalSpacer(16)
                FilledTonalButton(
                    onClick = { isSheetVisible.value = true },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    enabled = state is State.Success,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.location_on),
                        contentDescription = null
                    )

                    HorizontalSpacer(8)
                    Text(text = branchTitle)
                }
            }
        }

        item {
            Column {
                val profileOptions = options(interactor)

                profileOptions.forEach { item ->
                    ProfileOptionButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(item.titleRes),
                        painter = painterResource(item.iconRes),
                        action = item.action
                    )
                }
            }

            FooterSpacer()
        }
    }
}

@DefaultPreview
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
            topBar = { ProfileTopBar(interactor = ProfileInteractor.dummy) },
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
                        branches = branches.map { it.toItemModel() },
                        branchName = branch1.name,
                        name = "${user1.firstName} ${user1.lastName}",
                        phone = user1.phone
                    )
                ),
                interactor = ProfileInteractor.dummy
            )
        }
    }
}