package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.extensions.models.getPrice
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.LazyColumn
import com.orka.myfinances.ui.managers.navigation.NavigationManager

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun CheckoutContent(
    modifier: Modifier,
    items: List<BasketItem>,
    clients: List<Client>,
    viewModel: CheckoutScreenViewModel,
    navigationManager: NavigationManager
) {
    val price = rememberSaveable { mutableIntStateOf(items.getPrice()) }
    val description = rememberSaveable { mutableStateOf("") }
    val selectedClientId = rememberSaveable(clients) { mutableStateOf(clients.firstOrNull()?.id?.value) }
    val selectedClient = clients.find { it.id.value == selectedClientId.value }
    val menuExpanded = rememberSaveable { mutableStateOf(false) }
    val splitButtonMenuExpanded = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.checkout)) })
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 8.dp)) {

                Column(modifier = Modifier.weight(1f)) {

                    TextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.attach_money),
                                contentDescription = stringResource(R.string.price)
                            )
                        },
                        value = price.intValue.toString(),
                        onValueChange = {
                            val p = it.toIntOrNull()
                            if (p != null) price.intValue = p
                        }
                    )
                }

                HorizontalSpacer(8)
                Box {
                    SplitButtonLayout(
                        leadingButton = {
                            SplitButtonDefaults.LeadingButton(
                                onClick = {
                                    selectedClient?.let {
                                        viewModel.sell(
                                            basket = Basket(price.intValue, description.value, items),
                                            client = it
                                        )
                                        navigationManager.back()
                                    }
                                }
                            ) {
                                Text(text = stringResource(R.string.sell))
                            }
                        },
                        trailingButton = {
                            SplitButtonDefaults.TrailingButton(
                                onClick = { splitButtonMenuExpanded.value = true }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.arrow_drop_down),
                                    contentDescription = stringResource(R.string.down)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = splitButtonMenuExpanded.value,
                        onDismissRequest = { splitButtonMenuExpanded.value = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.order)) },
                            onClick = {
                                selectedClient?.let {
                                    viewModel.order(
                                        basket = Basket(price.intValue, description.value, items),
                                        client = it
                                    )
                                    navigationManager.back()
                                }
                                splitButtonMenuExpanded.value = false
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                arrangementSpace = 8.dp,
                contentPadding = PaddingValues(horizontal = 8.dp),
                items = items,
                item = { itemModifier, item ->
                    CheckoutItemCard(
                        modifier = itemModifier,
                        item = item
                    )
                }
            )

            ExposedDropDownTextField(
                modifier = Modifier.fillMaxWidth(),
                text = selectedClient?.firstName ?: stringResource(R.string.clients),
                label = stringResource(R.string.clients),
                menuExpanded = menuExpanded.value,
                onExpandChange = { menuExpanded.value = it },
                onDismissRequested = { menuExpanded.value = false },
                items = clients,
                itemText = { it.firstName },
                onItemSelected = { selectedClientId.value = it.id.value }
            )

            VerticalSpacer(4)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description.value,
                minLines = 3,
                onValueChange = { description.value = it },
                label = { Text(text = stringResource(R.string.description)) }
            )
        }
    }
}
