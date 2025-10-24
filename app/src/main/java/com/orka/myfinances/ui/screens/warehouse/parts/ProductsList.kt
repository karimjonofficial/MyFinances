package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.ui.screens.home.components.ProductCard

@Composable
fun ProductsList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    products: List<Product>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(products.size) { index ->
            ProductCard(product = products[index]) {}//TODO implement click
        }
    }
}