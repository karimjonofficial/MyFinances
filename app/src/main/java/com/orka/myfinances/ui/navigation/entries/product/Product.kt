package com.orka.myfinances.ui.navigation.entries.product

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.products.ProductScreen

@OptIn(ExperimentalMaterial3Api::class)
fun productEntry(
    modifier: Modifier,
    destination: Destination.Product
): NavEntry<Destination> = entry(destination) {

    ProductScreen(
        modifier = modifier,
        product = destination.product,
        onEdit = {},
        onAddToCart = {}
    )
}