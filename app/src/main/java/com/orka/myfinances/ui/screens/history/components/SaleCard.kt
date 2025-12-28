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
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.fixtures.resources.models.sale.sale1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview

@Composable
fun SaleCard(
    modifier: Modifier = Modifier,
    sale: Sale,
    onClick: (Sale) -> Unit
) {
    ListItem(
        modifier = modifier,
        model = sale,
        painter = painterResource(R.drawable.shopping_bag_outlined),
        headlineText = sale.items[0].product.title.name,
        supportingText = "${sale.items.size} items",
        price = sale.price.toString(),
        dateTime = sale.dateTime.toString(),
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
                SaleCard(modifier = Modifier.fillMaxWidth(), sale = sale1) { }
            }
        }
    }
}