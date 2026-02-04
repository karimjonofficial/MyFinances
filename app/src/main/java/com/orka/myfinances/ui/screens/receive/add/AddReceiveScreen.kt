package com.orka.myfinances.ui.screens.receive.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.IntegerTextField
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.managers.Navigator

@Composable
fun AddReceiveScreen(
    modifier: Modifier = Modifier,
    category: Category,
    state: State,
    viewModel: AddReceiveScreenViewModel,
    navigator: Navigator
) {
    val title = retain { mutableStateOf<ProductTitle?>(null) }
    val amount = rememberSaveable { mutableStateOf<Int?>(0) }
    val price = rememberSaveable { mutableStateOf<Int?>(0) }
    val salePrice = rememberSaveable { mutableStateOf<Int?>(0) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = modifier,
        title = category.name,
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 8.dp)) {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        val t = title.value
                        val a = amount.value
                        val p = price.value
                        val sp = salePrice.value

                        if (
                            t != null && a != null && a > 0 &&
                            p != null && p > 0 &&
                            sp != null && sp > 0
                        ) {
                            viewModel.add(
                                title = t,
                                price = p,
                                salePrice = sp,
                                amount = a,
                                description = description.value
                            )
                            navigator.back()
                        }
                    }
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
                items = if(state is State.Success<*>) state.value as List<ProductTitle> else emptyList(),
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
                onValueChange = { amount.value = it }
            )

            VerticalSpacer(8)
            IntegerTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.price)) },
                value = price.value,
                onValueChange = { amount.value = it }
            )

            VerticalSpacer(8)
            IntegerTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.sale_price)) },
                value = salePrice.value,
                onValueChange = { amount.value = it }
            )

            VerticalSpacer(8)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.description)) },
                value = description.value ?: "",
                onValueChange = { description.value = it },
                minLines = 3
            )
        }
    }
}