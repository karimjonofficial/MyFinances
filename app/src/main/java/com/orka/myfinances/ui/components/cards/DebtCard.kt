package com.orka.myfinances.ui.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.debt1
import com.orka.myfinances.fixtures.resources.models.debt2
import com.orka.myfinances.format.LocalFormatter
import com.orka.myfinances.lib.ui.extensions.description
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.components.ListItem
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.models.card.DebtCardModel
import com.orka.myfinances.ui.screens.debt.list.toCardModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun DebtCard(
    modifier: Modifier = Modifier,
    debt: DebtCardModel,
    onClick: () -> Unit
) {
    val formatter = LocalFormatter.current
    ListItem(
        modifier = modifier,
        painter = painterResource(R.drawable.money),
        headlineText = debt.name,
        supportingText = debt.description.description(),
        price = stringResource(R.string.uzs_f, formatter.formatNumber(debt.price)),
        dateTime = formatter.formatDateTime(debt.dateTime),
        onClick = { onClick() }
    )
}

@Preview
@Composable
private fun DebtCardPreview() {
    MyFinancesTheme {
        ScaffoldPreview(title = "Debt") { paddingValues ->
            Column(modifier = Modifier.scaffoldPadding(paddingValues)) {
                repeat(5) {
                    DebtCard(
                        modifier = Modifier.fillMaxWidth(),
                        debt = debt1.toCardModel(),
                        onClick = {}
                    )

                    DebtCard(
                        modifier = Modifier.fillMaxWidth(),
                        debt = debt2.toCardModel(),
                        onClick = {}
                    )
                }
            }
        }
    }
}
