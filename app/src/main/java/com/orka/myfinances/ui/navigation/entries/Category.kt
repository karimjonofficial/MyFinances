package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.folder.category.CategoryScreen
import com.orka.myfinances.ui.screens.product.list.ProductTitlesContent
import com.orka.myfinances.ui.screens.stock.StockContent
import kotlinx.coroutines.launch

private const val STOCK_TAB_INDEX = 0
private const val PRODUCTS_TAB_INDEX = 1

fun categoryEntry(
    modifier: Modifier,
    destination: Destination.Category,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "${destination.id.value}",
        initializer = { factory.categoryViewModel(destination.id)}
    )
    val state = viewModel.uiState.collectAsState()

    CategoryScreen(
        modifier = modifier,
        interactor = viewModel,
        state = state.value
    ) { paddingValues, state ->
        val modifier = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is State.Success -> {
                val pagerState = rememberPagerState(pageCount = { 2 })
                val coroutineScope = rememberCoroutineScope()

                Column(modifier = modifier) {
                    PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
                        Tab(
                            selected = pagerState.currentPage == STOCK_TAB_INDEX,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        STOCK_TAB_INDEX
                                    )
                                }
                            },
                            text = { Text(text = stringResource(R.string.warehouse)) }
                        )

                        Tab(
                            selected = pagerState.currentPage == PRODUCTS_TAB_INDEX,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        PRODUCTS_TAB_INDEX
                                    )
                                }
                            },
                            text = { Text(text = stringResource(R.string.products)) }
                        )
                    }

                    HorizontalPager(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        state = pagerState,
                    ) { page ->
                        val stockViewModel = viewModel(
                            key = "stock_${state.value.id.value}",
                            initializer = { factory.stockItemsViewModel(state.value.id) }
                        )
                        val productViewModel = viewModel(
                            key = "product-title_${state.value.id.value}",
                            initializer = { factory.productTitlesViewModel(state.value.id) }
                        )
                        val stockState = stockViewModel.uiState.collectAsState()
                        val productState = productViewModel.uiState.collectAsState()
                        when (page) {

                            STOCK_TAB_INDEX -> {
                                StockContent(
                                    modifier = Modifier.fillMaxSize(),
                                    interactor = stockViewModel,
                                    state = stockState.value,
                                    contentPadding = PaddingValues(
                                        vertical = 8.dp,
                                        horizontal = 16.dp
                                    ),
                                    onStockItemClick = stockViewModel::addToBasket
                                )
                            }

                            PRODUCTS_TAB_INDEX -> {
                                ProductTitlesContent(
                                    modifier = Modifier.fillMaxSize(),
                                    state = productState.value,
                                    interactor = productViewModel
                                )
                            }
                        }
                    }
                }
            }

            is State.Loading -> {
                LoadingScreen(
                    modifier = modifier,
                    message = state.message.str()
                )
            }

            is State.Failure -> {
                FailureScreen(
                    modifier = modifier,
                    message = state.error.str(),
                    retry = viewModel::initialize
                )
            }
        }
    }
}