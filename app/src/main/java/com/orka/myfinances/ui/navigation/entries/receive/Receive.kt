package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import coil3.compose.AsyncImage
import com.orka.myfinances.R
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.industrialDrill
import com.orka.myfinances.fixtures.resources.models.receive.receive1
import com.orka.myfinances.fixtures.resources.safetyHelmet
import com.orka.myfinances.fixtures.resources.straps
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.managers.Navigator

fun receiveEntry(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    destination: Destination.Receive
): NavEntry<Destination> = entry(destination) {

    ReceiveScreen(
        modifier = modifier,
        navigator = navigator,
        receive = destination.receive
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    receive: Receive
) {
    val items = listOf(
        ReceivedItem(
            name = "Industrial Drill V2",
            amount = "Amount: 10 units",
            price = "$2,500",
            imageUrl = industrialDrill
        ),
        ReceivedItem(
            name = "Pro Safety Helmets",
            amount = "Amount: 50 units",
            price = "$1,250",
            imageUrl = safetyHelmet
        ),
        ReceivedItem(
            name = "Heavy Duty Straps",
            amount = "Amount: 100 units",
            price = "$1,100",
            imageUrl = straps
        )
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.receive_detail)) },
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
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(R.string.total_price),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$${receive.price}",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        HorizontalDivider(Modifier.padding(vertical = 16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.date_range),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            HorizontalSpacer(12)
                            Column {
                                Text(
                                    text = stringResource(R.string.date_time),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${receive.dateTime}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.person),
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    HorizontalSpacer(16)

                    Column {
                        Text(
                            text = "${receive.user.firstName} ${receive.user.lastName}",
                            style = MaterialTheme.typography.titleSmall
                        )

                        Text(
                            text = receive.user.profession ?: "No profession detected",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Text(
                    text = "${stringResource(R.string.items)}(${receive.items.size})",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            items(items = items) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )

                        HorizontalSpacer(16)
                        Column(Modifier.weight(1f)) {

                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                text = item.amount,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = item.price,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            item {
                Column {
                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    VerticalSpacer(8)
                    OutlinedCard {
                        Text(
                            text = receive.description.description(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.print),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.print_stock_labels))
                    }

                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.share),
                            contentDescription = null
                        )

                        HorizontalSpacer(8)
                        Text(text = stringResource(R.string.share_report))
                    }
                }
            }

            item { HorizontalSpacer(32) }
        }
    }
}

data class ReceivedItem(
    val name: String,
    val amount: String,
    val price: String,
    val imageUrl: String
)

@Preview
@Composable
private fun ReceiveScreenPreview() {
    ReceiveScreen(
        navigator = DummyNavigator(),
        receive = receive1
    )
}