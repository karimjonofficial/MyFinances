package com.orka.myfinances.ui.screens.receive.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.folder.categories
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.CommentTextField
import com.orka.myfinances.lib.ui.components.IntegerTextField
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.list.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AddReceiveScreen(
    modifier: Modifier = Modifier,
    category: Category,
    state: State,
    viewModel: AddReceiveScreenViewModel
) {
    val title = retain { mutableStateOf<ProductTitle?>(null) }
    val amount = rememberSaveable { mutableStateOf<Int?>(null) }
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val salePrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val totalPrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    fun refreshTotalPrice(amount: Int?, price: Int?) {
        if (amount != null && price != null) {
            totalPrice.value = amount * price
        }
    }

    Scaffold(
        modifier = modifier,
        title = category.name,
        bottomBar = {
            val t = title.value
            val a = amount.value
            val p = price.value
            val sp = salePrice.value
            val tp = totalPrice.value
            val saveable = t != null && a != null && a > 0 &&
                    p != null && p > 0 &&
                    sp != null && sp > 0 &&
                    tp != null

            BottomAppBar(contentPadding = PaddingValues(horizontal = 8.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    enabled = saveable,
                    onClick = { viewModel.add(
                            title = t,
                            price = p,
                            salePrice = sp,
                            amount = a,
                            totalPrice = tp,
                            description = description.value
                        ) }
                ) {
                    Text(text = stringResource(R.string.add))
                }
            }
        }
    ) { paddingValues ->
        val expanded = rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedExposedDropDownTextField(
                modifier = Modifier.fillMaxWidth(),
                text = title.value?.name ?: stringResource(R.string.products),
                label = stringResource(R.string.products),
                menuExpanded = expanded.value,
                onExpandChange = { expanded.value = it },
                onDismissRequested = { expanded.value = false },
                items = if (state is State.Success<*>) state.value as List<ProductTitle> else emptyList(),
                itemText = { it.name },
                onItemSelected = {
                    price.value = it.defaultPrice
                    salePrice.value = it.defaultSalePrice
                    title.value = it
                }
            )

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
}

@Preview
@Composable
private fun AddReceiveScreenPreview() {
    val viewModel = viewModel {
        AddReceiveScreenViewModel(
            category = category1,
            add = { null },
            get = { null },
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            navigator = DummyNavigator(),
            logger = DummyLogger()
        )
    }
    MyFinancesTheme {
        AddReceiveScreen(
            modifier = Modifier.fillMaxSize(),
            category = category1,
            state = State.Success(categories),
            viewModel = viewModel
        )
    }
}