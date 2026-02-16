package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.ui.screens.home.components.ProductTitleCard
import com.orka.myfinances.ui.screens.warehouse.viewmodel.ProductTitleUiModel

@Composable
fun ProductTitlesList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    productTitles: List<ProductTitleUiModel>,
    onProductClick: (ProductTitle) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(items = productTitles) { productTitle ->
            ProductTitleCard(
                productTitle = productTitle.model,
                onClick = { onProductClick(productTitle.title) }
            )
        }
    }
}