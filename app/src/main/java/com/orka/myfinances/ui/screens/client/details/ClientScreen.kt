package com.orka.myfinances.ui.screens.client.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen(
    modifier: Modifier = Modifier,
    state: State<ClientScreenModel>,
    interactor: ClientInteractor
) {
    StatefulScreen(
        modifier = modifier,
        state = state,
        topBar = {
            TopAppBar(
                title = {
                    val client = (state as? State.Success)?.value
                    Text(text = client?.fullName ?: stringResource(R.string.loading))
                },
                navigationIcon = {
                    IconButton(onClick = interactor::back) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { modifier, model ->
        Column(modifier = modifier) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    painter = painterResource(R.drawable.account_circle_outlined),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )

                VerticalSpacer(8)
                Text(text = model.phone ?: stringResource(R.string.no_phone_number))

                VerticalSpacer(8)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.width(160.dp),
                        onClick = {}
                    ) {
                        Text(text = stringResource(R.string.message))
                    }

                    HorizontalSpacer(8)
                    Button(
                        modifier = Modifier.width(160.dp),
                        onClick = {}
                    ) {
                        Text(text = stringResource(R.string.call))
                    }
                }
            }

            VerticalSpacer(16)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))

            ListItem(
                modifier = Modifier.clickable { },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.call_outlined),
                        contentDescription = model.phone
                    )
                },
                headlineContent = { Text(text = stringResource(R.string.phone_number)) },
                supportingContent = { Text(text = model.phone ?: stringResource(R.string.no_phone_number)) },
                trailingContent = {
                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = stringResource(R.string.phone_number)
                    )
                }
            )

            ListItem(
                modifier = Modifier.clickable { },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.map_outlined),
                        contentDescription = model.address
                    )
                },
                headlineContent = { Text(text = stringResource(R.string.address)) },
                supportingContent = { Text(text = model.address ?: stringResource(R.string.no_address)) },
                trailingContent = {
                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = stringResource(R.string.address)
                    )
                }
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ClientScreenPreview() {
    MyFinancesTheme {
        ClientScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(client1.map()),
            interactor = ClientInteractor.dummy
        )
    }
}