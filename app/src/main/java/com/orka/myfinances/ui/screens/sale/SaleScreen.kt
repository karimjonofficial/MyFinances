package com.orka.myfinances.ui.screens.sale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.components.UserCard
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.components.ClientCard
import com.orka.myfinances.ui.screens.debt.components.DescriptionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    sale: Sale
) {
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
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
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
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
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
                    }
                }
            }

            item {
                ClientCard(
                    client = sale.client,
                    navigator = navigator
                )
            }

            item {
                UserCard(
                    user = sale.user,
                    onClick = {}
                )
            }

            item {
                DividedList(
                    title = stringResource(R.string.items_purchased),
                    items = sale.items,
                    itemTitle = { it.product.title.name },
                    itemSupportingText = { "${it.amount}" }
                )
            }

            if(!sale.description.isNullOrBlank()) {
                item {
                    DescriptionCard(description = sale.description)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaleScreenPreview() {
    SaleScreen(
        navigator = DummyNavigator(),
        sale = sale1
    )
}