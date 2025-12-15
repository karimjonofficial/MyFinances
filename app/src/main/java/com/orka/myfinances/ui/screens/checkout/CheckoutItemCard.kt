package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.fixtures.resources.models.basket.basketItem1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview

@Composable
fun CheckoutItemCard(
    modifier: Modifier = Modifier,
    item: BasketItem
) {
    Column(modifier = modifier) {
        Text(text = item.product.title.name)
        Text(text = "$${item.product.salePrice} x ${item.amount} = $${item.product.salePrice * item.amount}")
    }
}

@Preview
@Composable
private fun CheckoutItemCardPreview() {

    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = "Checkout"
    ) { paddingValues ->

        Column(
            modifier = Modifier.scaffoldPadding(paddingValues).padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(10) {
                CheckoutItemCard(
                    modifier = Modifier.fillMaxWidth(),
                    item = basketItem1
                )
            }
        }
    }
}