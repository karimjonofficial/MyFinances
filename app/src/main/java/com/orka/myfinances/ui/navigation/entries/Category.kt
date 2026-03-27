package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.folder.category.CategoryScreen
import com.orka.myfinances.ui.screens.product.list.ProductTitlesContent
import com.orka.myfinances.ui.screens.stock.StockContent

fun categoryEntry(
    modifier: Modifier,
    destination: Destination.Category,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val categoryViewModel = viewModel(
        key = "${destination.id.value}",
        initializer = { factory.categoryViewModel(destination.id) }
    )
    val stockViewModel = viewModel(
        key = "stock_${destination.id.value}",
        initializer = { factory.stockItemsViewModel(destination.id) }
    )
    val productViewModel = viewModel(
        key = "products_${destination.id.value}",
        initializer = { factory.productTitlesViewModel(destination.id) }
    )

    val categoryState by categoryViewModel.uiState.collectAsState()
    val stockState by stockViewModel.uiState.collectAsState()
    val productState by productViewModel.uiState.collectAsState()

    CategoryScreen(
        modifier = modifier,
        state = categoryState,
        interactor = categoryViewModel,
        tabContent = { index ->
            val fillMaxSizeModifier = Modifier.fillMaxSize()
            when (index) {
                0 -> StockContent(
                    modifier = fillMaxSizeModifier,
                    interactor = stockViewModel,
                    state = stockState,
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                )

                1 -> ProductTitlesContent(
                    modifier = fillMaxSizeModifier,
                    state = productState,
                    interactor = productViewModel
                )
            }
        }
    )
}