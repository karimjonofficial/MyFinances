package com.orka.myfinances.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.fixtures.resources.models.product1
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (Product) -> Unit
) {
    val d = product.description.ifEmpty { stringResource(R.string.no_description_provided) }

    ListItem(
        modifier = modifier.clickable { onClick(product) },
        headlineContent = { Text(text = product.name) },
        supportingContent = { Text(text = d) },
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.star_outlined),
                contentDescription = product.name
            )
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = product.name
            )
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProductCardPreview() {
    MyFinancesTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProductCard(
                product = product1.copy(description = "Some product is there")
            ) {

            }
        }
    }
}