package com.orka.myfinances.ui.screens.checkout

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.format.LocalFormatter
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.SectionTitle
import com.orka.myfinances.lib.ui.components.spacer.FooterSpacer
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.components.textfield.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.textfield.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.printer.PrinterStatus
import com.orka.myfinances.ui.models.item.CheckoutItemModel
import com.orka.myfinances.ui.models.item.ClientItemModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutContent(
    modifier: Modifier = Modifier,
    printerStatus: PrinterStatus,
    items: List<CheckoutItemModel>,
    selectedClient: ClientItemModel?,
    hiddenPrice: String,
    price: Int?,
    description: String?,
    printReceipt: Boolean,
    exposed: Boolean,
    transactionType: TransactionType,
    paymentType: PaymentType,
    deliveryDate: LocalDate,
    dueDate: LocalDate,
    onTransactionTypeChange: (TransactionType) -> Unit,
    onPaymentTypeChange: (PaymentType) -> Unit,
    onPriceChange: (Int?) -> Unit,
    onDescriptionChange: (String?) -> Unit,
    onPrintReceiptChange: () -> Unit,
    onOpenClients: () -> Unit,
    onPickDeliveryDate: () -> Unit,
    onPickDueDate: () -> Unit
) {
    val scrollState = rememberScrollState()
    val expanded = rememberSaveable(items) { mutableStateOf(false) }
    val formatter = LocalFormatter.current

    val remainders = remember(price) {
        listOf(10, 100, 1000, 10000, 100000)
            .map { price?.rem(it) ?: 0 }
            .filter { it in 1..<(price ?: 0) }
            .distinct()
            .sorted()
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 12.dp)
    ) {
        val itemsValue = if (expanded.value) items else {
            if (items.size < 5) items else items.take(4)
        }

        VerticalSpacer(8)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                DividedList(
                    modifier = Modifier.animateContentSize(),
                    title = stringResource(R.string.items_purchased),
                    items = itemsValue,
                    itemTitle = { it.title },
                    itemSupportingText = {
                        stringResource(R.string.uzs_f, formatter.formatNumber(it.price))
                    }
                )

                if (items.size > 4) {
                    VerticalSpacer(4)
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { expanded.value = !expanded.value },
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        text = stringResource(if (!expanded.value) R.string.expand else R.string.collapse)
                    )
                }
            }
        }

        VerticalSpacer(16)
        SectionTitle(text = stringResource(R.string.transaction))

        VerticalSpacer(8)
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            TransactionType.entries.forEachIndexed { index, type ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = TransactionType.entries.size),
                    onClick = { onTransactionTypeChange(type) },
                    selected = transactionType == type,
                    label = {
                        Text(
                            text = when (type) {
                                TransactionType.SELL -> stringResource(R.string.sell)
                                TransactionType.ORDER -> stringResource(R.string.order)
                            }
                        )
                    }
                )
            }
        }

        VerticalSpacer(16)
        SectionTitle(text = stringResource(R.string.payment))

        VerticalSpacer(8)
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            PaymentType.entries.forEachIndexed { index, type ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = PaymentType.entries.size),
                    onClick = { onPaymentTypeChange(type) },
                    selected = paymentType == type,
                    label = {
                        Text(
                            text = when (type) {
                                PaymentType.PAID -> stringResource(R.string.paid)
                                PaymentType.DEBT -> stringResource(R.string.debt)
                            }
                        )
                    }
                )
            }
        }

        VerticalSpacer(16)
        SectionTitle(text = stringResource(R.string.client))

        VerticalSpacer(8)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenClients() },
            value = selectedClient?.title ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = false,
            placeholder = { Text(text = stringResource(R.string.select_client)) },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.unfold_more),
                    contentDescription = null
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )

        if (transactionType == TransactionType.ORDER) {
            VerticalSpacer(16)
            SectionTitle(text = stringResource(R.string.delivery_date))

            VerticalSpacer(8)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPickDeliveryDate() },
                value = formatter.formatDate(deliveryDate.atStartOfDayIn(TimeZone.currentSystemDefault())),
                onValueChange = {},
                readOnly = true,
                enabled = false,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.calendar_today),
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        }

        if (paymentType == PaymentType.DEBT) {
            VerticalSpacer(16)
            SectionTitle(text = stringResource(R.string.due_date))

            VerticalSpacer(8)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPickDueDate() },
                value = formatter.formatDate(dueDate.atStartOfDayIn(TimeZone.currentSystemDefault())),
                onValueChange = {},
                readOnly = true,
                enabled = false,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.date_range),
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        }

        VerticalSpacer(16)
        SectionTitle(text = stringResource(R.string.total_price))

        VerticalSpacer(8)
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
            value = price,
            onValueChange = { onPriceChange(it) }
        )

        if (exposed) {
            VerticalSpacer(4)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.original_price) + ": ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = hiddenPrice,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (remainders.isNotEmpty()) {
            VerticalSpacer(8)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                remainders.forEach { remainder ->
                    SuggestionChip(
                        onClick = { onPriceChange(price?.minus(remainder)) },
                        label = {
                            Text(text = "-${formatter.formatNumber(remainder)} ${stringResource(R.string.uzs)}")
                        }
                    )
                }
            }
        }

        VerticalSpacer(16)
        OutlinedCommentTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { onDescriptionChange(it) }
        )

        if (printerStatus is PrinterStatus.Connected) {
            VerticalSpacer(8)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onPrintReceiptChange),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = printReceipt,
                    onCheckedChange = null
                )

                HorizontalSpacer(4)
                Text(
                    text = stringResource(R.string.print_receipt) + " (${printerStatus.printer.name})",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        FooterSpacer()
    }
}

@DefaultPreview
@Composable
private fun CheckoutContentPreview() {
    val items = listOf(
        CheckoutItemModel("Product1", 100000),
        CheckoutItemModel("Product2", 100000)
    )
    val status = PrinterStatus.Disconnected

    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CheckoutScreenBottomBar(
                transactionType = TransactionType.SELL,
                paymentType = PaymentType.PAID,
                selectedClient = null,
                onExecute = {}
            )
        },
        title = "Checkout"
    ) { paddingValues ->
        CheckoutContent(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp),
            printerStatus = status,
            items = items,
            hiddenPrice = "",
            selectedClient = null,
            price = 1000,
            description = null,
            printReceipt = true,
            exposed = false,
            transactionType = TransactionType.SELL,
            paymentType = PaymentType.PAID,
            deliveryDate = LocalDate(2026, 8, 20),
            dueDate = LocalDate(2026, 8, 20),
            onTransactionTypeChange = {},
            onPaymentTypeChange = {},
            onPriceChange = {},
            onDescriptionChange = {},
            onPrintReceiptChange = {},
            onOpenClients = {},
            onPickDeliveryDate = {},
            onPickDueDate = {}
        )
    }
}
