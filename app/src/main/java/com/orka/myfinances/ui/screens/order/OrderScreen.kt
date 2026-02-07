package com.orka.myfinances.ui.screens.order

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.fixtures.resources.models.order.order1
import com.orka.myfinances.fixtures.resources.models.order.order2
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.ui.components.PersonCard
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    order: Order,
) {
    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.order),
        bottomBar = {
            if(!order.completed) {
                BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {}) {
                        Text(text = stringResource(R.string.complete))
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.price) + ": $${order.price}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )

                    VerticalSpacer(16)
                    HorizontalDivider()
                    VerticalSpacer(16)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LabeledDate(
                            modifier = Modifier.weight(1f),
                            label = stringResource(R.string.registered_at),
                            date = order.dateTime.toString()
                        )

                        HorizontalSpacer(16)
                        VerticalDivider(modifier = Modifier.height(40.dp))
                        HorizontalSpacer(16)

                        LabeledDate(
                            modifier = Modifier.weight(1f),
                            label = if (order.completed) stringResource(R.string.completed_at) 
                                    else stringResource(R.string.completion_date),
                            date = order.endDateTime.toString()
                        )
                    }
                }
            }

            VerticalSpacer(8)
            DividedList(
                items = order.items,
                title = { item -> item.product.title.name },
                description = { item -> "${item.amount}" }
            )

            VerticalSpacer(8)
            PersonCard(
                person = order.client,
                onClick = {}
            )

            VerticalSpacer(8)
            PersonCard(
                person = order.user,
                onClick = {}
            )
        }
    }
}

@Composable
private fun LabeledDate(
    modifier: Modifier = Modifier,
    label: String,
    date: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = date,
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            textAlign = TextAlign.Center,
            maxLines = 1,
            softWrap = false
        )
    }
}

@Preview
@Composable
private fun OrderScreenPreview() {
    MyFinancesTheme {
        OrderScreen(
            modifier = Modifier.fillMaxSize(),
            order = order1
        )
    }
}

@Preview
@Composable
private fun CompletedOrderScreenPreview() {
    MyFinancesTheme {
        OrderScreen(
            modifier = Modifier.fillMaxSize(),
            order = order2
        )
    }
}
