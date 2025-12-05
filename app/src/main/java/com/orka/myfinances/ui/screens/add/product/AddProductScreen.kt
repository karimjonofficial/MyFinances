package com.orka.myfinances.ui.screens.add.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.data.repositories.product.models.PropertyModel
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.components.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenState
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    modifier: Modifier = Modifier,
    warehouse: Warehouse,
    state: AddProductScreenState,
    viewModel: AddProductScreenViewModel,
    navigationManager: NavigationManager
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.add_product)) }
            )
        }
    ) { paddingValues ->
        val m = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is AddProductScreenState.Loading -> LoadingScreen(modifier = m)

            is AddProductScreenState.Success -> {
                val warehouses = state.warehouses
                val scrollState = rememberScrollState()
                val menuVisible = rememberSaveable { mutableStateOf(false) }

                val name = rememberSaveable { mutableStateOf("") }
                val price = rememberSaveable { mutableIntStateOf(0) }
                val salePrice = rememberSaveable { mutableIntStateOf(0) }
                val description = rememberSaveable { mutableStateOf("") }
                val warehouseId = rememberSaveable { mutableIntStateOf(warehouse.id.value) }

                val selectedWarehouse = warehouses.find { it.id.value == warehouseId.intValue }
                val fields = selectedWarehouse!!.template.fields
                val properties =
                    rememberSaveable { mutableStateListOf<PropertyModel<*>?>() }.apply {
                        repeat(fields.size) { add(null) }
                    }

                Column(modifier = m) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text(text = stringResource(R.string.name)) }
                        )

                        ExposedDropDownTextField(
                            text = selectedWarehouse.name,
                            label = stringResource(R.string.warehouse),
                            menuExpanded = menuVisible.value,
                            onExpandChange = { menuVisible.value = it },
                            onDismissRequested = { menuVisible.value = false },
                            items = warehouses,
                            itemText = { it.name },
                            onItemSelected = { warehouseId.intValue = it.id.value }
                        )

                        if (fields.isNotEmpty()) {
                            VerticalSpacer(8)
                            Text(
                                modifier = Modifier.align(Alignment.Start),
                                text = stringResource(R.string.properties)
                            )
                            VerticalSpacer(4)
                            PropertiesList(fields = fields, properties = properties)
                            VerticalSpacer(8)
                        }

                        OutlinedTextField(
                            value = price.intValue.toString(),
                            onValueChange = { price.intValue = it.toInt() },
                            label = { Text(text = stringResource(R.string.price)) }
                        )

                        OutlinedTextField(
                            value = salePrice.intValue.toString(),
                            onValueChange = { salePrice.intValue = it.toInt() },
                            label = { Text(text = stringResource(R.string.sale_price)) }
                        )

                        OutlinedTextField(
                            value = description.value,
                            onValueChange = { description.value = it },
                            label = { Text(text = stringResource(R.string.description)) }
                        )
                    }

                    BottomAppBar {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    val nameValue = name.value
                                    val priceValue = price.intValue
                                    val salePriceValue = salePrice.intValue
                                    val descriptionValue = description.value
                                    val warehouseValue = warehouseId.intValue
                                    val propertiesValue = properties.filterNotNull()

                                    if (
                                        propertiesValue.size == fields.size &&
                                        nameValue.isNotEmpty() &&
                                        priceValue > 0 &&
                                        salePriceValue > 0 && salePriceValue > priceValue &&
                                        descriptionValue.isNotEmpty() &&
                                        warehouseValue > 0
                                    ) {
                                        val request = AddProductRequest(
                                            name = nameValue,
                                            warehouseId = warehouseValue,
                                            price = priceValue,
                                            salePrice = salePriceValue,
                                            properties = propertiesValue,
                                            description = descriptionValue
                                        )
                                        viewModel.addProduct(request)
                                        navigationManager.back()
                                    }
                                }
                            ) {
                                Text(text = stringResource(R.string.save))
                            }
                        }
                    }
                }
            }
        }
    }
}