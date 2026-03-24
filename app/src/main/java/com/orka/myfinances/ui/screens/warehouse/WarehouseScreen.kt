package com.orka.myfinances.ui.screens.warehouse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.warehouse.parts.ProductTitlesContent
import com.orka.myfinances.ui.screens.warehouse.parts.StockContent
import com.orka.myfinances.ui.screens.warehouse.parts.WarehouseScreenTopBar
import kotlinx.coroutines.launch

private const val STOCK_TAB_INDEX = 0
private const val PRODUCTS_TAB_INDEX = 1

@Composable
fun WarehouseScreen(
    modifier: Modifier = Modifier,
    categoryId: Id,
    factory: Factory
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    val createdPages = remember { mutableStateListOf(STOCK_TAB_INDEX) }
    var screenTitle by remember { mutableStateOf<String?>(null) }
    var onAddProduct by remember { mutableStateOf({}) }
    var onReceive by remember { mutableStateOf({}) }

    Scaffold(
        modifier = modifier,
        topBar = {
            WarehouseScreenTopBar(
                title = screenTitle ?: stringResource(R.string.loading),
                onAddProductClick = onAddProduct,
                onAddReceive = onReceive
            )
        }
    ) { paddingValues ->
        LaunchedEffect(pagerState.currentPage) {
            if (pagerState.currentPage !in createdPages) createdPages += pagerState.currentPage
        }
        Column(modifier = Modifier.scaffoldPadding(paddingValues)) {
            PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    selected = pagerState.currentPage == STOCK_TAB_INDEX,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(STOCK_TAB_INDEX) }
                    },
                    text = { Text(text = stringResource(R.string.warehouse)) }
                )

                Tab(
                    selected = pagerState.currentPage == PRODUCTS_TAB_INDEX,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(PRODUCTS_TAB_INDEX) }
                    },
                    text = { Text(text = stringResource(R.string.products)) }
                )
            }

            HorizontalPager(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                state = pagerState,
            ) { page ->
                if (page !in createdPages) {
                    LoadingScreen(modifier = Modifier.fillMaxWidth())
                    return@HorizontalPager
                }

                when (page) {
                    STOCK_TAB_INDEX -> {
                        val viewModel = viewModel(
                            key = "warehouse-stock-${categoryId.value}",
                            initializer = { factory.warehouseStockViewModel(categoryId) }
                        )
                        val state = viewModel.uiState.collectAsState()
                        val title = viewModel.title.collectAsState()

                        LaunchedEffect(title.value) {
                            if (title.value != null) screenTitle = title.value
                        }

                        LaunchedEffect(pagerState.currentPage) {
                            if (pagerState.currentPage == STOCK_TAB_INDEX) {
                                onAddProduct = viewModel::addProduct
                                onReceive = viewModel::receive
                            }
                        }

                        StockContent(
                            modifier = Modifier.fillMaxSize(),
                            interactor = viewModel,
                            state = state.value,
                            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                            onStockItemClick = viewModel::addToBasket
                        )
                    }

                    PRODUCTS_TAB_INDEX -> {
                        val viewModel = viewModel(
                            key = "warehouse-products-${categoryId.value}",
                            initializer = { factory.warehouseProductTitlesViewModel(categoryId) }
                        )
                        val state = viewModel.uiState.collectAsState()
                        val title = viewModel.title.collectAsState()

                        LaunchedEffect(title.value) {
                            if (title.value != null) screenTitle = title.value
                        }

                        LaunchedEffect(pagerState.currentPage) {
                            if (pagerState.currentPage == PRODUCTS_TAB_INDEX) {
                                onAddProduct = viewModel::addProduct
                                onReceive = viewModel::receive
                            }
                        }

                        ProductTitlesContent(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                            state = state.value,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}