package com.orka.myfinances.ui.components.cards

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
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.product.productTitle1
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.ui.models.card.ProductTitleCardModel
import com.orka.myfinances.ui.screens.product.list.toCardModel

@Composable
fun ProductTitleCard(
    modifier: Modifier = Modifier,
    productTitle: ProductTitleCardModel,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onClick() },
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.star_outlined),
                contentDescription = productTitle.title
            )
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = productTitle.title
            )
        },
        supportingContent = {
            Text(
                text = productTitle.description ?: stringResource(R.string.no_description_provided),
                maxLines = 2
            )
        },
        content = { Text(text = productTitle.title) },
    )
}

@DefaultPreview
@Composable
private fun ProductTitleCardPreview() {
    ScaffoldPreview(title = "Product Title Card") {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProductTitleCard(
                productTitle = productTitle1.toCardModel(),
                onClick = {}
            )
        }
    }
}