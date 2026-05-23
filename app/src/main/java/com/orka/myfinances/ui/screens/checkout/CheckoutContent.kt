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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.name
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.SectionTitle
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
    hiddenPrice: String,
    uiState: CheckoutUIState,
    onOpenClients: () -> Unit,
    onOpenAddClient: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val formatter = remember {
        NumberFormat.getIntegerInstance(Locale.US).apply {
            isGroupingUsed = true
        }
    }

    val price = uiState.price ?: 0
    val remainders = remember(price) {
        listOf(10, 100, 1000, 10000, 100000)
            .map { price % it }
            .filter { it in 1..<price }
            .distinct()
            .sorted()
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 12.dp)
    ) {
        DividedList(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.items_purchased),
            items = items,
            itemTitle = { it.title },
            itemSupportingText = { it.price }
        )

        VerticalSpacer(16)
        SectionTitle(text = stringResource(R.string.client))

        VerticalSpacer(4)
        Row {
            if (uiState.selectedClient == null && uiState.newClientFirstName == null) {
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
                    text = uiState.selectedClient?.title ?: uiState.newClientFirstName!!,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        VerticalSpacer(12)
        SectionTitle(text = stringResource(R.string.total_price))

        VerticalSpacer(4)
        OutlinedIntegerTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.attach_money),
                    contentDescription = stringResource(R.string.total_price)
                )
            },
            trailingIcon = {
                Text(
                    stringResource(R.string.uzs),
                    fontWeight = FontWeight.Bold
                )
            },
            value = uiState.price ?: 0,
            onValueChange = { uiState.price = it }
        )

        if (uiState.exposed) {
            VerticalSpacer(8)
            Text(
                modifier = Modifier.align(Alignment.End),
                text = hiddenPrice,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }

        if (remainders.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                remainders.forEach { remainder ->
                    SuggestionChip(
                        onClick = { uiState.price = price - remainder },
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
            value = uiState.description,
            onValueChange = { uiState.description = it }
        )

        VerticalSpacer(8)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { uiState.printReceipt = !uiState.printReceipt },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.printReceipt,
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
                uiState = CheckoutUIState().apply { price = 1000 },
                interactor = CheckoutScreenInteractor.dummy,
            )
        },
        title = "Checkout"
    ) { paddingValues ->
        CheckoutContent(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp),
            items = items,
            hiddenPrice = "",
            uiState = CheckoutUIState().apply { price = 200012 },
            onOpenClients = {},
            onOpenAddClient = {},
        )
    }
}

@Preview
@Composable
private fun CheckoutContentWithExposedPricePreview() {
    val items = listOf(
        BasketItemCardModel("Product1", "10,000.00 UZS x 10 = 100,000.00 UZS"),
        BasketItemCardModel("Product2", "10,000.00 UZS x 10 = 100,000.00 UZS")
    )

    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CheckoutScreenBottomBar(
                uiState = CheckoutUIState().apply { price = 1000 },
                interactor = CheckoutScreenInteractor.dummy,
            )
        },
        title = "Checkout"
    ) { paddingValues ->
        CheckoutContent(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp),
            items = items,
            hiddenPrice = "10, 000 UZS",
            uiState = CheckoutUIState().apply { 
                price = 200012
                exposed = true
            },
            onOpenClients = {},
            onOpenAddClient = {},
        )
    }
}

@Preview(device = "id:pixel_10_pro_xl")
@Composable
private fun CheckoutContentWithSelectedClientPreview() {
    val items = listOf(
        BasketItemCardModel("Product1", "10,000.00 UZS x 10 = 100,000.00 UZS"),
        BasketItemCardModel("Product2", "10,000.00 UZS x 10 = 100,000.00 UZS")
    )
    val client = ClientItemModel(
        id = id1,
        title = name
    )

    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CheckoutScreenBottomBar(
                uiState = CheckoutUIState().apply { price = 1000 },
                interactor = CheckoutScreenInteractor.dummy,
            )
        },
        title = "Checkout"
    ) { paddingValues ->
        CheckoutContent(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp),
            items = items,
            hiddenPrice = "",
            uiState = CheckoutUIState().apply { 
                price = 200012
                selectedClient = client
            },
            onOpenClients = {},
            onOpenAddClient = {},
        )
    }
}
