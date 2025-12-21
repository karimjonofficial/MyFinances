package com.orka.myfinances.ui.screens.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.managers.navigation.NavigationManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStockItemScreen(
    modifier: Modifier = Modifier,
    warehouse: Warehouse,
    products: List<Product>,
    viewModel: AddStockItemScreenViewModel,
    navigationManager: NavigationManager
) {
    val amount = rememberSaveable { mutableIntStateOf(0) }
    val productId = rememberSaveable { mutableStateOf<Int?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(text = warehouse.name) })
        },
        bottomBar = {
            BottomAppBar {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        val id = productId.value
                        val a = amount.intValue

                        if (id != null && a > 0) {
                            viewModel.add(products.first { it.id.value == id }, a)
                            navigationManager.back()
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
            modifier = Modifier.scaffoldPadding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedExposedDropDownTextField(
                text = products.firstOrNull { it.id.value == productId.value }?.title?.name ?: stringResource(R.string.stock_items),
                label = stringResource(R.string.stock_items),
                menuExpanded = expanded.value,
                onExpandChange = { expanded.value = it },
                onDismissRequested = { expanded.value = false },
                items = products,
                itemText = { it.title.name },
                onItemSelected = { productId.value = it.id.value }
            )

            VerticalSpacer(8)
            TextField(
                value = amount.intValue.toString(),
                onValueChange = {
                    val a = it.toIntOrNull()
                    if (a != null) amount.intValue = a
                }
            )
        }
    }
}