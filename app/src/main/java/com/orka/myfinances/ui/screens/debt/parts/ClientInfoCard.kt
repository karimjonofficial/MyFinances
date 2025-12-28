package com.orka.myfinances.ui.screens.debt.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.ui.screens.debt.ClientAction
import com.orka.myfinances.ui.screens.debt.components.Avatar

@Composable
fun ClientInfoCard(
    modifier: Modifier = Modifier,
    client: Client
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Avatar(contentDescription = stringResource(R.string.client_avatar))

                Spacer(Modifier.size(16.dp))
                Column {

                    Text(
                        text = client.firstName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            painter = painterResource(R.drawable.crown),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.size(4.dp))
                        Text(
                            text = stringResource(R.string.premium_card_client),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Row(modifier = Modifier.fillMaxWidth()) {

                ClientAction(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.call_outlined),
                    text = stringResource(R.string.call),
                    onClick = {}
                )

                ClientAction(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.sms),
                    text = stringResource(R.string.text),
                    onClick = {}
                )

                ClientAction(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.mail),
                    text = stringResource(R.string.email),
                    onClick = {}
                )
            }
        }
    }
}