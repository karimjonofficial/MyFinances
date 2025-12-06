package com.orka.myfinances.ui.screens.client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen(
    modifier: Modifier = Modifier,
    client: Client
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "${client.firstName} ${client.lastName}") }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.scaffoldPadding(paddingValues)) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    modifier = Modifier.size(160.dp),
                    painter = painterResource(R.drawable.account_circle_outlined),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )

                Text(text = client.phone)

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
                        contentDescription = client.phone
                    )
                },
                headlineContent = { Text(text = stringResource(R.string.phone_number)) },
                supportingContent = { Text(text = client.phone) },
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
                        contentDescription = client.address
                    )
                },
                headlineContent = { Text(text = stringResource(R.string.address)) },
                supportingContent = { Text(text = client.address) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ClientScreenPreview() {
    ClientScreen(
        modifier = Modifier.fillMaxSize(),
        client = client1
    )
}