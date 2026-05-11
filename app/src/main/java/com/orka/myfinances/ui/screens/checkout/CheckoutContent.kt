package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.description
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.screens.checkout.viewmodel.BasketItemCardModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CheckoutContent(
    modifier: Modifier = Modifier,
    items: List<BasketItemCardModel>,
    price: Int,
    description: String?,
    printReceipt: Boolean,
    selectedClient: ClientItemModel?,
    newClientFirstName: String?,
    onOpenClients: () -> Unit,
    onOpenAddClient: () -> Unit,
    onPriceChanged: (Int?) -> Unit,
    onDescriptionChanged: (String?) -> Unit,
    onPrintReceiptChanged: (Boolean) -> Unit,
) {
    val formatter = remember {
        NumberFormat.getIntegerInstance(Locale.US).apply {
            isGroupingUsed = true
        }
    }

    val remainders = remember(price) {
        listOf(10, 100, 1000, 10000)
            .map { price % it }
            .filter { it in 1..<price }
            .distinct()
            .sorted()
    }

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
            if(selectedClient == null && newClientFirstName == null) {
                Row {
                    Button(
                        contentPadding = ButtonDefaults.contentPaddingFor(
                            buttonHeight = ButtonDefaults.MinHeight,
                            hasEndIcon = true
                        ),
                        onClick = onOpenClients
                    ) {
                        Text(text = stringResource(R.string.select_client))
                        HorizontalSpacer(8)
                        Icon(
                            painter = painterResource(R.drawable.unfold_more),
                            contentDescription = null
                        )
                    }

                    HorizontalSpacer(4)
                    Button(onClick = onOpenAddClient) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = null
                        )
                    }
                }
            } else {
                Text(
                    modifier = Modifier.clickable { onOpenClients() },
                    text = selectedClient?.title ?: newClientFirstName!!,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

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

        if (remainders.isNotEmpty()) {
            VerticalSpacer(4)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                remainders.forEach { remainder ->
                    SuggestionChip(
                        onClick = { onPriceChanged(price - remainder) },
                        label = {
                            Text(text = "-${formatter.format(remainder)} ${stringResource(R.string.uzs)}")
                        }
                    )
                }
            }
        }

        VerticalSpacer(8)
        OutlinedCommentTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { onDescriptionChanged(it) }
        )

        VerticalSpacer(8)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onPrintReceiptChanged(!printReceipt) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = printReceipt,
                onCheckedChange = null
            )

            HorizontalSpacer(4)
            Text(text = stringResource(R.string.print_receipt))
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
        bottomBar = {
            CheckoutScreenBottomBar(
                price = 1000,
                selectedClient = null,
                newClientFirstName = null,
                newClientLastName = null,
                newClientPatronymic = null,
                newClientPhone = null,
                newClientAddress = null,
                interactor = CheckoutScreenInteractor.dummy,
                description = description,
                printReceipt = true
            )
        },
        title = "Checkout"
    ) { paddingValues ->
        CheckoutContent(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp),
            items = items,
            price = 10231,
            description = "",
            printReceipt = false,
            selectedClient = null,
            newClientFirstName = null,
            onOpenClients = {},
            onOpenAddClient = {},
            onPriceChanged = {},
            onDescriptionChanged = {},
            onPrintReceiptChanged = {}
        )
    }
}
