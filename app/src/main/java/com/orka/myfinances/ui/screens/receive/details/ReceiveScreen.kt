package com.orka.myfinances.ui.screens.receive.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.DescriptionCard
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.components.UserCard
import com.orka.myfinances.application.viewmodels.sale.details.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveScreen(
    modifier: Modifier = Modifier,
    interactor: ReceiveScreenInteractor,
    state: State<ReceiveScreenModel>
) {
    StatefulScreen(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.receive_detail)) },
                navigationIcon = {
                    IconButton(onClick = interactor::back) {
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
        state = state
    ) { modifier, uiModel ->
        LazyColumn(
            modifier = modifier.padding(16.dp),
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
                            text = uiModel.price,
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
                                    text = uiModel.dateTime,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                UserCard(
                    user = uiModel.user.map(),
                    onClick = {}
                )
            }

            item {
                VerticalSpacer(8)
                DividedList(
                    title = stringResource(R.string.items),
                    items = uiModel.items,
                    itemTitle = { item -> item.name },
                    itemSupportingText = { item -> item.amount }
                )
            }

            if (!uiModel.description.isNullOrBlank()) {
                item {
                    DescriptionCard(description = uiModel.description)
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.print),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.print_stock_labels))
                    }

                    OutlinedButton(onClick = {}) {
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