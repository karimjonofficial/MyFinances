package com.orka.myfinances.ui.screens.debt.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.debt1
import com.orka.myfinances.fixtures.resources.models.debt2
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.screens.debt.viewmodel.toModel
import com.orka.myfinances.ui.screens.history.components.ListItem

@Composable
fun DebtCard(
    modifier: Modifier = Modifier,
    debt: DebtCardModel,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier,
        painter = painterResource(R.drawable.money),
        headlineText = debt.name,
        supportingText = debt.description.description(),
        price = debt.price,
        dateTime = debt.dateTime,
        onClick = { onClick() }
    )
}

@Preview
@Composable
private fun DebtCardPreview() {
    ScaffoldPreview(title = "Debt") { paddingValues ->
        Column(modifier = Modifier.scaffoldPadding(paddingValues)) {
            repeat(5) {

                DebtCard(
                    modifier = Modifier.fillMaxWidth(),
                    debt = debt1.toModel(),
                    onClick = {}
                )

                DebtCard(
                    modifier = Modifier.fillMaxWidth(),
                    debt = debt2.toModel(),
                    onClick = {}
                )
            }
        }
    }
}