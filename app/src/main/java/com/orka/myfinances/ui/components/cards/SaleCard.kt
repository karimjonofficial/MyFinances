package com.orka.myfinances.ui.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.sale.sale1
import com.orka.myfinances.format.LocalFormatter
import com.orka.myfinances.lib.ui.extensions.scaffoldPadding
import com.orka.myfinances.lib.ui.components.ListItem
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.models.card.SaleCardModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun SaleCard(
    modifier: Modifier = Modifier,
    sale: SaleCardModel,
    onClick: () -> Unit
) {
    val formatter = LocalFormatter.current
    ListItem(
        modifier = modifier,
        painter = painterResource(R.drawable.shopping_bag_outlined),
        headlineText = sale.title,
        supportingText = stringResource(R.string.items_f, formatter.formatNumber(sale.size)),
        price = stringResource(R.string.uzs_f, formatter.formatNumber(sale.price)),
        dateTime = formatter.formatDateTime(sale.dateTime),
        onClick = onClick
    )
}

@Preview
@Composable
private fun SaleCardPreview() {
    MyFinancesTheme {
        ScaffoldPreview(
            modifier = Modifier.fillMaxSize(),
            title = stringResource(R.string.history)
        ) { paddingValues ->

            Column(
                modifier = Modifier.scaffoldPadding(paddingValues)
            ) {
                repeat(10) {
                    SaleCard(
                        modifier = Modifier.fillMaxWidth(),
                        sale = SaleCardModel(
                            title = sale1.items.joinToString { it.product.title.name },
                            price = sale1.price,
                            size = sale1.items.size,
                            dateTime = sale1.dateTime
                        ),
                        onClick = {}
                    )
                }
            }
        }
    }
}
