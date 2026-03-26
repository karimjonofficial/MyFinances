package com.orka.myfinances.ui.screens.sale.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.format.FormatDateTimeImpl
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.resources.models.sale.sale1
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.components.ClientCard
import com.orka.myfinances.ui.components.UserCard
import com.orka.myfinances.lib.ui.components.DescriptionCard
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenInteractor
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenModel
import com.orka.myfinances.application.viewmodels.sale.details.toUiModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleScreen(
    modifier: Modifier = Modifier,
    state: State<SaleScreenModel>,
    interactor: SaleScreenInteractor
) {
    StatefulScreen(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.sale_details)) },
                navigationIcon = {
                    IconButton(onClick = { interactor.back() }) {
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
    ) { modifier, model ->
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
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
                            text = model.price,
                            style = MaterialTheme.typography.displaySmall
                        )

                        VerticalSpacer(12)
                        Text(
                            text = model.dateTime,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                ClientCard(
                    model = model.client,
                    onClick = { interactor.navigateToClient(model.clientId) }
                )
            }

            item {
                UserCard(
                    user = model.user,
                    onClick = {}
                )
            }

            item {
                DividedList(
                    title = stringResource(R.string.items_purchased),
                    items = model.items,
                    itemTitle = { it.title },
                    itemSupportingText = { it.supportingText }
                )
            }

            if (!model.description.isNullOrBlank()) {
                item {
                    DescriptionCard(description = model.description)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaleScreenPreview() {
    val dummyInteractor = object : SaleScreenInteractor {
        override fun initialize() {}
        override fun navigateToClient(clientId: Id) {}
        override fun back() {}
    }
    MyFinancesTheme {
        SaleScreen(
            state = State.Success(
                value = sale1.toUiModel(
                    formatPrice = FormatPriceImpl(),
                    formatDateTime = FormatDateTimeImpl(),
                    formatDecimal = FormatDecimalImpl()
                )
            ),
            interactor = dummyInteractor
        )
    }
}