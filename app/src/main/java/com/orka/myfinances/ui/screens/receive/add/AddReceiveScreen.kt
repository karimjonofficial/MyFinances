package com.orka.myfinances.ui.screens.receive.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.application.viewmodels.receive.bottomsheet.ProductTitleBottomSheetInteractor
import com.orka.myfinances.lib.ui.components.CommentTextField
import com.orka.myfinances.lib.ui.components.IntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.theme.MyFinancesTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddReceiveScreen(
    modifier: Modifier = Modifier,
    state: State<AddReceiveScreenModel>,
    interactor: AddReceiveScreenInteractor,
    productTitleSheetState: State<ChunkMapState<ProductTitleItemModel>>,
    productTitleSheetInteractor: ProductTitleBottomSheetInteractor
) {
    val title = retain { mutableStateOf<ProductTitleItemModel?>(null) }
    val amount = rememberSaveable { mutableStateOf<Int?>(null) }
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val salePrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val exposedPrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val totalPrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    fun refreshTotalPrice(amount: Int?, price: Int?) {
        if (amount != null && price != null) {
            totalPrice.value = amount * price
        }
    }

    StatefulScreen(
        modifier = modifier,
        title = if (state.value != null) state.value!!.title else stringResource(R.string.loading),
        state = state,
        bottomBar = {
            AddReceiveScreenBottomBar(
                interactor = interactor,
                productTitle = title.value,
                price = price.value,
                salePrice = salePrice.value,
                exposedPrice = exposedPrice.value,
                amount = amount.value,
                totalPrice = totalPrice.value,
                description = description.value
            )
        }
    ) { modifier, _ ->
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val productsLoaded = productTitleSheetState.value?.content?.isNotEmpty() == true

            if (title.value == null) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = productTitleSheetState !is State.Loading || productsLoaded,
                    onClick = {
                        productTitleSheetInteractor.refresh()
                        sheetVisible.value = true
                    }
                ) {
                    Text(text = stringResource(R.string.products))
                }
            } else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title.value!!.title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                VerticalSpacer(8)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = productTitleSheetState !is State.Loading || productsLoaded,
                    onClick = {
                        productTitleSheetInteractor.refresh()
                        sheetVisible.value = true
                    }
                ) {
                    Text(text = stringResource(R.string.products))
                }
            }

            VerticalSpacer(8)
            IntegerTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.amount)) },
                value = amount.value,
                onValueChange = {
                    amount.value = it
                    refreshTotalPrice(it, price.value)
                }
            )

            VerticalSpacer(8)
            IntegerTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.price)) },
                value = price.value,
                onValueChange = {
                    price.value = it
                    refreshTotalPrice(amount.value, it)
                }
            )

            VerticalSpacer(8)
            IntegerTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.sale_price)) },
                value = salePrice.value,
                onValueChange = { salePrice.value = it }
            )

            VerticalSpacer(8)
            IntegerTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.exposed_price)) },
                value = exposedPrice.value,
                onValueChange = { exposedPrice.value = it }
            )

            VerticalSpacer(8)
            IntegerTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.total_price)) },
                value = totalPrice.value,
                onValueChange = { totalPrice.value = it }
            )

            VerticalSpacer(8)
            CommentTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description.value ?: "",
                onValueChange = { description.value = it }
            )
        }
    }

    if (sheetVisible.value) {
        SelectProductTitleBottomSheet(
            state = productTitleSheetState,
            selectedProductTitle = title.value,
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            onSelected = {
                title.value = it
                price.value = it.defaultPrice
                salePrice.value = it.defaultSalePrice
                exposedPrice.value = it.defaultExposedPrice
                refreshTotalPrice(amount.value, it.defaultPrice)
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            onLoadMore = productTitleSheetInteractor::loadMore
        )
    }
}

@Preview
@Composable
private fun AddReceiveScreenPreview() {
    MyFinancesTheme {
        AddReceiveScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(AddReceiveScreenModel("Category 1")),
            interactor = AddReceiveScreenInteractor.dummy,
            productTitleSheetState = State.Success(
                ChunkMapState(
                    count = 0,
                    pageIndex = 1,
                    nextPageIndex = null,
                    previousPageIndex = null,
                    content = emptyMap()
                )
            ),
            productTitleSheetInteractor = object : ProductTitleBottomSheetInteractor {
                override fun initialize() {}
                override fun refresh() {}
                override fun loadMore() {}
            }
        )
    }
}