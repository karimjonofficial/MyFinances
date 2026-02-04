package com.orka.myfinances.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.fixtures.resources.models.order.order1
import com.orka.myfinances.fixtures.resources.models.order.order2
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview

@Composable
fun OrderCard(
    modifier: Modifier = Modifier,
    order: Order,
    onClick: (Order) -> Unit
) {

    if(!order.completed) {
        OutlinedCard(
            modifier = modifier,
            onClick = { onClick(order) }
        ) {
            OrderCardContent(order = order)
        }
    } else {
        Card(
            modifier = modifier,
            onClick = { onClick(order) },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            OrderCardContent(order = order)
        }
    }
}

@Composable
private fun OrderCardContent(
    modifier: Modifier = Modifier,
    order: Order
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "${order.client.firstName} ${order.client.lastName}",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "$${order.price}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            VerticalSpacer(8)
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    painter = painterResource(R.drawable.calendar_today),
                    contentDescription = null
                )

                HorizontalSpacer(4)
                Text(
                    text = "${order.endDateTime}",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            VerticalSpacer(8)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        color = if(!order.completed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.onPrimary
                    )
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        painter = painterResource(R.drawable.shopping_bag_outlined),
                        tint = if(!order.completed) LocalContentColor.current else MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )

                    HorizontalSpacer(4)
                    Text(
                        text = "${order.items.size} items",
                        color = if(!order.completed) LocalContentColor.current else MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OrderCardPreview() {
    ScaffoldPreview(
        title = "Orders"
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            repeat(5) {
                OrderCard(
                    modifier = Modifier.fillMaxWidth(),
                    order = order1,
                    onClick = {}
                )

                OrderCard(
                    modifier = Modifier.fillMaxWidth(),
                    order = order2,
                    onClick = {}
                )
            }
        }
    }
}