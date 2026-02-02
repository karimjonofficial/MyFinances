package com.orka.myfinances.ui.screens.products.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.models.types.Range
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.RangeField
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun PropertiesList(
    modifier: Modifier = Modifier,
    fields: List<TemplateField>,
    onSuccess: (Int, PropertyModel<*>) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        fields.forEachIndexed { index, field ->
            VerticalSpacer(8)
            when (field.type) {
                "text" -> {
                    val value = rememberSaveable { mutableStateOf("") }

                    OutlinedTextField(
                        value = value.value,
                        onValueChange = {
                            value.value = it
                            if (it.isNotEmpty()) {
                                onSuccess(index, PropertyModel(field.id, it))
                            }
                        },
                        label = { Text(text = field.name) }
                    )
                }

                "number" -> {
                    val value = rememberSaveable { mutableStateOf<Int?>(0) }

                    OutlinedIntegerTextField(
                        value = value.value,
                        onValueChange = {
                            value.value = it
                            if (it != null && it > 0) {
                                onSuccess(index, PropertyModel(field.id, it))
                            }
                        },
                        label = { Text(text = field.name) }
                    )
                }

                "range" -> {
                    val min = rememberSaveable { mutableStateOf<Int?>(0) }
                    val max = rememberSaveable { mutableStateOf<Int?>(1) }

                    Text(modifier = Modifier.align(Alignment.Start), text = field.name)

                    RangeField(
                        modifier = Modifier.width(280.dp),
                        min = min.value,
                        max = max.value,
                        onMinValueChange = {
                            min.value = it
                            val maxValue = max.value
                            if (it != null && it > 0 && maxValue != null)
                                onSuccess(index, PropertyModel(field.id, Range(it, maxValue)))
                        },
                        onMaxValueChange = {
                            val minValue = min.value
                            max.value = it

                            if (minValue != null && minValue > 0 && it != null)
                                onSuccess(index, PropertyModel(field.id, Range(minValue, it)))
                        }
                    )
                }
            }
        }
    }
}