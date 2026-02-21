package com.orka.myfinances.ui.screens.order

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.orka.myfinances.fixtures.format.FormatDateImpl
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.order.order1
import com.orka.myfinances.fixtures.resources.models.order.order2
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.components.ClientCard
import com.orka.myfinances.ui.components.UserCard
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.components.DescriptionCard
import com.orka.myfinances.ui.screens.order.viewmodel.OrderScreenModel
import com.orka.myfinances.ui.screens.order.viewmodel.toScreenModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    state: State,
    navigator: Navigator
) {
    @Suppress("UNCHECKED_CAST")
    StatefulScreen<OrderScreenModel>(
        modifier = modifier,
        title = stringResource(R.string.order),
        state = state,
        bottomBar = { state ->
            if (!((state as? State.Success<OrderScreenModel>)?.value?.completed ?: false)) {
                SingleActionBottomBar(
                    buttonText = stringResource(R.string.complete),
                    action = {}//TODO
                )
            }
        }
    ) { modifier, order ->

        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = stringResource(R.string.total_price))

                    Text(
                        text = order.price,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    VerticalSpacer(16)
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    VerticalSpacer(16)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LabeledDate(
                            modifier = Modifier.weight(1f),
                            label = stringResource(R.string.registered_at),
                            date = order.startDate
                        )

                        HorizontalSpacer(16)
                        VerticalDivider(
                            modifier = Modifier.height(40.dp),
                            color = MaterialTheme.colorScheme.outline
                        )

                        HorizontalSpacer(16)
                        LabeledDate(
                            modifier = Modifier.weight(1f),
                            label = if (order.completed) stringResource(R.string.completed_at)
                            else stringResource(R.string.completion_date),
                            date = order.endDate
                        )
                    }
                }
            }

            VerticalSpacer(16)
            DividedList(
                title = stringResource(R.string.order_details),
                items = order.items,
                itemTitle = { item -> item.name },
                itemSupportingText = { item -> item.amount }
            )

            VerticalSpacer(16)
            Text(text = stringResource(R.string.customer_details))

            VerticalSpacer(8)
            ClientCard(
                model = order.client.model,
                onClick = { navigator.navigateToClient(order.client.client) }
            )

            VerticalSpacer(8)
            UserCard(
                user = order.user,
                onClick = {}
            )

            if(!order.description.isNullOrBlank()) {
                VerticalSpacer(8)
                DescriptionCard(description = order.description)
            }
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
            modifier = Modifier.basicMarquee(),
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
            state = State.Success(order1.toScreenModel(FormatPriceImpl(), FormatDateImpl(), FormatDecimalImpl())),
            navigator = DummyNavigator()
        )
    }
}

@Preview
@Composable
private fun CompletedOrderScreenPreview() {
    MyFinancesTheme {
        OrderScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(order2.toScreenModel(FormatPriceImpl(), FormatDateImpl(), FormatDecimalImpl())),
            navigator = DummyNavigator()
        )
    }
}
