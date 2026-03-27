package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.screens.checkout.viewmodel.BasketItemCardModel
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel

@Composable
fun CheckoutContent(
    modifier: Modifier,
    items: List<BasketItemCardModel>,
    clients: List<ClientItemModel>,
    price: Int,
    description: String?,
    printReceipt: Boolean,
    selectedClient: ClientItemModel?,
    printerConnected: Boolean,
    onClientSelected: (Id) -> Unit,
    onPriceChanged: (Int?) -> Unit,
    onDescriptionChanged: (String?) -> Unit,
    onPrintReceiptChanged: (Boolean) -> Unit,
) {
    val menuExpanded = rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        DividedList(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.items_purchased),
            items = items,
            itemTitle = { it.title },
            itemSupportingText = { it.price }
        )

        VerticalSpacer(16)
        Row {
            OutlinedExposedDropDownTextField(
                modifier = Modifier.weight(1f),
                text = selectedClient?.name ?: stringResource(R.string.clients),
                label = stringResource(R.string.clients),
                menuExpanded = menuExpanded.value,
                onExpandChange = { menuExpanded.value = it },
                onDismissRequested = { menuExpanded.value = false },
                items = clients,
                itemText = { it.name },
                onItemSelected = { onClientSelected(it.id) }
            )

            HorizontalSpacer(8)
            OutlinedIntegerTextField(
                modifier = Modifier.width(160.dp),
                label = stringResource(R.string.total_price),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.attach_money),
                        contentDescription = stringResource(R.string.price)
                    )
                },
                value = price,
                onValueChange = { onPriceChanged(it) }
            )
        }

        VerticalSpacer(8)
        OutlinedCommentTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { onDescriptionChanged(it) }
        )

        if (printerConnected) {
            VerticalSpacer(8)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPrintReceiptChanged(!printReceipt) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = printReceipt,
                    onCheckedChange = { onPrintReceiptChanged(it) }
                )
                Text(text = stringResource(R.string.print_receipt))
            }
        }

        VerticalSpacer(8)
    }
}

@Preview
@Composable
private fun CheckoutContentPreview() {
    val items = listOf(
        BasketItemCardModel("Product1", "10,000.00 UZS x 10 = 100,000.00 UZS"),
        BasketItemCardModel("Product2", "10,000.00 UZS x 10 = 100,000.00 UZS")
    )

    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = "Checkout"
    ) { paddingValues ->
        CheckoutContent(
            modifier = Modifier.scaffoldPadding(paddingValues).padding(horizontal = 8.dp),
            items = items,
            clients = clients.map { it.map() },
            price = 1000,
            printerConnected = true,
            description = "",
            printReceipt = false,
            selectedClient = null,
            onClientSelected = {},
            onPriceChanged = {},
            onDescriptionChanged = {},
            onPrintReceiptChanged = {}
        )
    }
}