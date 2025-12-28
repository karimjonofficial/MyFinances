package com.orka.myfinances.ui.screens.debt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.fixtures.resources.models.debt1
import com.orka.myfinances.fixtures.resources.models.debt2
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.screens.history.components.ListItem

@Composable
fun DebtCard(
    modifier: Modifier = Modifier,
    debt: Debt,
    onClick: (Debt) -> Unit
) {
    ListItem(
        modifier = modifier,
        model = debt,
        painter = painterResource(R.drawable.money),
        headlineText = debt.client.firstName,
        supportingText = debt.description ?: stringResource(R.string.no_description_provided),
        price = "$${debt.price}",
        dateTime = debt.endDateTime.toString(),
        onClick = { onClick(debt) }
    )
}

@Preview
@Composable
private fun DebtCardPreview() {
    ScaffoldPreview(title = "Debt") { paddingValues ->
        Column(
            modifier = Modifier.scaffoldPadding(paddingValues)
        ) {
            repeat(5) {
                DebtCard(modifier = Modifier.fillMaxWidth(), debt = debt1, onClick = {})
                DebtCard(modifier = Modifier.fillMaxWidth(), debt = debt2, onClick = {})
            }
        }
    }
}