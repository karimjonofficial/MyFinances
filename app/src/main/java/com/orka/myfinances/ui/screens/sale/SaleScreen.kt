package com.orka.myfinances.ui.screens.sale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.sale.sale1
import com.orka.myfinances.fixtures.resources.person
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.navigation.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    sale: Sale
) {
    val items = listOf(
        SaleItem("Premium Leather Briefcase", "Qty: 1 • SKU: LTHR-BF-001", "$620.00"),
        SaleItem("Matching Card Holder", "Qty: 2 • SKU: LTHR-CH-042", "$140.00"),
        SaleItem("Executive Planner Set", "Qty: 1 • SKU: STAT-EX-99", "$340.00")
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.sale_details)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.back() }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.more_vert),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f).wrapContentHeight(),
                    shape = RoundedCornerShape(50)
                ) {

                    Icon(
                        painter = painterResource(R.drawable.receipt_long),
                        contentDescription = null
                    )

                    HorizontalSpacer(8)
                    Text(text = stringResource(R.string.download_receipt))
                }

                HorizontalSpacer(16)
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
                ) {

                    Icon(
                        painter = painterResource(R.drawable.share),
                        contentDescription = null
                    )

                    HorizontalSpacer(8)
                    Text(text = stringResource(R.string.share_info))
                }
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.total_sale_value),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "$${sale.price}",
                            style = MaterialTheme.typography.displaySmall
                        )

                        VerticalSpacer(12)
                        Text(
                            text = "${sale.dateTime}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "${sale.id}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                SectionHeader(title = stringResource(R.string.client))

                PersonRow(
                    modifier = Modifier.fillMaxWidth(),
                    name = "${sale.client.firstName} ${sale.client.lastName}",
                    subtitle = sale.client.address ?: "No address detected",//TODO
                    icon = painterResource(R.drawable.person)
                )
            }

            item {
                SectionHeader(title = "Seller")

                SellerRow(
                    modifier = Modifier.fillMaxWidth(),
                    name = "${sale.user.firstName} ${sale.user.lastName}",
                    role = sale.user.profession ?: "No profession detected",//TODO
                    imageUrl = person
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.items_purchased),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Surface(
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Text(
                            text = "${sale.items.size}",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            )
                        )
                    }
                }
            }

            items(items = items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {

                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                text = item.details,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = item.price,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                Column {
                    SectionHeader(title = stringResource(R.string.transaction_notes))

                    OutlinedCard {
                        Text(
                            text = sale.description.description(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    VerticalSpacer(8)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SaleScreenPreview() {
    SaleScreen(
        navigator = DummyNavigator(),
        sale = sale1
    )
}