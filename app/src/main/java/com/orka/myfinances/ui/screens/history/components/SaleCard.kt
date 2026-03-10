package com.orka.myfinances.ui.screens.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.resources.models.sale.sale1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.ListItem
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview

@Composable
fun SaleCard(
    modifier: Modifier = Modifier,
    sale: SaleCardModel,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier,
        painter = painterResource(R.drawable.shopping_bag_outlined),
        headlineText = sale.title,
        supportingText = sale.size,
        price = sale.price,
        dateTime = sale.dateTime,
        onClick = onClick
    )
}

@Preview
@Composable
private fun SaleCardPreview() {
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
                        price = FormatPriceImpl().formatPrice(sale1.price.toDouble()),
                        size = "${sale1.items.size} items",
                        dateTime = "12.02.26"
                    ),
                    onClick = {}
                )
            }
        }
    }
}