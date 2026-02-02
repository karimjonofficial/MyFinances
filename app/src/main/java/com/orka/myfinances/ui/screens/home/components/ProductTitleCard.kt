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
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.fixtures.resources.models.product.productTitle1
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun ProductTitleCard(
    modifier: Modifier = Modifier,
    productTitle: ProductTitle,
    onClick: (ProductTitle) -> Unit
) {
    val d = productTitle.description.description()

    ListItem(
        modifier = modifier.clickable { onClick(productTitle) },
        headlineContent = { Text(text = productTitle.name) },
        supportingContent = { Text(text = d) },
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.star_outlined),
                contentDescription = productTitle.name
            )
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = productTitle.name
            )
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProductTitleCardPreview() {
    MyFinancesTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProductTitleCard(
                productTitle = productTitle1,
                onClick = {}
            )
        }
    }
}