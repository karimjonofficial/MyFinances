package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.entry.lazyColumnEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun clientsEntry(
    modifier: Modifier,
    destination: Destination.Clients,
    navigationManager: NavigationManager
): NavEntry<Destination> = lazyColumnEntry(
    modifier = modifier,
    destination = destination,
    viewModel = destination.viewModel as ClientsScreenViewModel,
    topBar = {
        TopAppBar(
            title = { Text(text = stringResource(R.string.clients)) }
        )
    },
    item = { modifier, client ->
        ClientCard(
            modifier = modifier,
            client = client,
            onClick = { navigationManager.navigateToClient(it) }
        )
    }
)

@Composable
fun ClientCard(
    modifier: Modifier = Modifier,
    client: Client,
    onClick: (Client) -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onClick(client) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${client.firstName[0]}${client.lastName[0]}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        HorizontalSpacer(8)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${client.firstName} ${client.lastName}",
                fontWeight = FontWeight.Bold
            )

            VerticalSpacer(4)
            Text(text = client.phone)
        }

        HorizontalSpacer(8)
        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = client.firstName
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ClientCardPreview() {
    Scaffold { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(10) {
                    ClientCard(
                        modifier = Modifier.fillMaxWidth(),
                        client = client1,
                        onClick = {}
                    )
                }
            }
        }
    }
}