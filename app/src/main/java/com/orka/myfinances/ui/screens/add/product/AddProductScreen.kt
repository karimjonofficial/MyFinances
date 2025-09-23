package com.orka.myfinances.ui.screens.add.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Range
import com.orka.myfinances.lib.ui.LoadingScreen
import com.orka.myfinances.lib.ui.components.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.RangeField
import com.orka.myfinances.ui.screens.add.product.AddProductRequest
import com.orka.myfinances.ui.screens.add.product.PropertyModel
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenState
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel

@Composable
fun AddProductScreen(
    modifier: Modifier = Modifier,
    warehouse: Warehouse,
    state: AddProductScreenState,
    viewModel: AddProductScreenViewModel
) {
    when (state) {
        is AddProductScreenState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is AddProductScreenState.Success -> {
            val warehouses = state.warehouses
            val fields = warehouse.template.fields
            val scrollState = rememberScrollState()
            val menuVisible = rememberSaveable { mutableStateOf(false) }

            val name = rememberSaveable { mutableStateOf("") }
            val price = rememberSaveable { mutableIntStateOf(0) }
            val salePrice = rememberSaveable { mutableIntStateOf(0) }
            val description = rememberSaveable { mutableStateOf("") }
            val warehouseId = rememberSaveable { mutableIntStateOf(warehouse.id.value) }
            val properties = rememberSaveable { mutableStateListOf<PropertyModel<*>?>() }.apply {
                repeat(fields.size) { add(null) }
            }

            Column(modifier = modifier) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text(text = stringResource(R.string.name)) }
                    )

                    ExposedDropDownTextField(
                        text = warehouses[warehouseId.intValue].name,
                        label = stringResource(R.string.warehouse),
                        menuExpanded = menuVisible.value,
                        onExpandChange = { menuVisible.value = it },
                        onDismissRequested = { menuVisible.value = false },
                        items = warehouses,
                        itemText = { it.name },
                        onItemSelected = { warehouseId.intValue = it.id.value }
                    )

                    Column(
                        horizontalAlignment = Alignment.Companion.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        fields.forEachIndexed { i, field ->
                            when (field.type) {
                                "text" -> {
                                    val value = rememberSaveable { mutableStateOf("") }

                                    OutlinedTextField(
                                        value = value.value,
                                        onValueChange = {
                                            value.value = it
                                            if (it.isNotEmpty()) {
                                                properties.add(PropertyModel(field, it))
                                            }
                                        },
                                        label = { Text(text = field.name) }
                                    )
                                }

                                "number" -> {
                                    val value = rememberSaveable { mutableIntStateOf(0) }

                                    OutlinedTextField(
                                        value = value.intValue.toString(),
                                        onValueChange = {
                                            val n = it.toIntOrNull()
                                            if (n != null && n > 0) {
                                                value.intValue = n
                                                properties.add(PropertyModel(field, n))
                                            }
                                        },
                                        label = { Text(text = field.name) }
                                    )
                                }

                                "range" -> {
                                    val min = rememberSaveable { mutableIntStateOf(0) }
                                    val max = rememberSaveable { mutableIntStateOf(1) }

                                    RangeField(
                                        min = min.intValue,
                                        max = max.intValue,
                                        onMinValueChange = {
                                            val maxValue = max.intValue
                                            if (it > 0 && it < maxValue) {
                                                properties.add(
                                                    PropertyModel(
                                                        field = field,
                                                        value = Range(it, maxValue)
                                                    )
                                                )
                                                min.intValue = it
                                            }
                                        },
                                        onMaxValueChange = {
                                            val minValue = min.intValue

                                            if (minValue > 0 && minValue < it) {
                                                properties.add(
                                                    PropertyModel(
                                                        field = field,
                                                        value = Range(minValue, it)
                                                    )
                                                )
                                                max.intValue = it
                                            }
                                        }
                                    )
                                }
                            }
                        }
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