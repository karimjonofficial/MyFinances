package com.orka.myfinances.ui.screens.debt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.fixtures.resources.models.debt1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.ui.screens.debt.components.Avatar
import com.orka.myfinances.ui.screens.debt.components.DateCard
import com.orka.myfinances.ui.screens.debt.components.DescriptionCard
import com.orka.myfinances.ui.screens.debt.components.EmphasizedDateCard
import com.orka.myfinances.ui.screens.debt.parts.ClientInfoCard
import com.orka.myfinances.ui.screens.debt.parts.NotificationStatusCard
import kotlin.time.Clock


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtDetailScreen(
    modifier: Modifier = Modifier,
    debt: Debt,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onMarkAsPaidClick: () -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.debt_details),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onEditClick) {
                        Text(
                            text = stringResource(R.string.edit),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
            ) {
                Button(
                    onClick = onMarkAsPaidClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.check_circle),
                        contentDescription = null
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(stringResource(R.string.mark_as_paid))
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroSection(
                    modifier = Modifier.fillMaxSize(),
                    debt = debt
                )
            }

            item {
                NotificationStatusCard(
                    notified = debt.notified,
                    lastSent = stringResource(R.string.last_sent_today_9_41_am)
                )
            }

            item { ClientInfoCard(client = debt.client) }
            item { TimelineAndStaffCard(debt = debt) }

            item {
                debt.description?.let {
                    DescriptionCard(description = debt.description)
                }
            }
        }
    }
}

@Composable
fun HeroSection(
    modifier: Modifier = Modifier,
    debt: Debt
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (debt.endDateTime > Clock.System.now()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = CircleShape
                        )
                )

                Spacer(Modifier.size(8.dp))

                Text(
                    text = "Overdue",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        Text(
            text = "$${debt.price}",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(R.string.total_amount_owed),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ClientAction(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {

    TextButton(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(0.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {

            Icon(
                painter = painter,
                contentDescription = text
            )

            Text(text, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun TimelineAndStaffCard(
    modifier: Modifier = Modifier,
    debt: Debt
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = stringResource(R.string.timeline_and_staff),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                DateCard(
                    modifier = Modifier.weight(1f),
                    label = "Start Date",
                    date = debt.dateTime,
                    painter = painterResource(R.drawable.calendar_today),
                    contentDescription = stringResource(R.string.created_at)
                )

                if (debt.endDateTime > Clock.System.now()) {
                    EmphasizedDateCard(
                        modifier = Modifier.weight(1f),
                        label = "Due Date",
                        date = debt.endDateTime,
                        painter = painterResource(R.drawable.error),
                        contentDescription = stringResource(R.string.error)
                    )
                } else {
                    DateCard(
                        modifier = Modifier.weight(1f),
                        label = "Due Date",
                        date = debt.endDateTime,
                        painter = painterResource(R.drawable.calendar_today),
                        contentDescription = stringResource(R.string.created_at)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {}
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Avatar(contentDescription = stringResource(R.string.assigned_to_avatar))

                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = stringResource(R.string.assigned_to),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = debt.user.firstName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun DebtDetailScreenPreview() {
    MaterialTheme {
        DebtDetailScreen(
            debt = debt1,
            onBackClick = {},
            onEditClick = {},
            onMarkAsPaidClick = {}
        )
    }
}
