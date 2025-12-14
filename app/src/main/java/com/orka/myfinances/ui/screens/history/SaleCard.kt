package com.orka.myfinances.ui.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.fixtures.resources.models.sale.sale1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun SaleCard(
    modifier: Modifier = Modifier,
    sale: Sale,
    onClick: (Sale) -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onClick(sale) },
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.shopping_bag_outlined),
                contentDescription = null
            )
        },
        headlineContent = { Text(text = sale.items[0].product.title.name, softWrap = false) },
        supportingContent = { Text(text = "${sale.items.size} items") },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = sale.price.toString())
                Text(text = sale.dateTime.toString())
            }
        }
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