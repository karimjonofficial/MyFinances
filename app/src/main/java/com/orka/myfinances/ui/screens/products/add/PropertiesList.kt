package com.orka.myfinances.ui.screens.products.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.product.Range
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.product.models.PropertyModel
import com.orka.myfinances.lib.ui.components.RangeField
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun PropertiesList(
    modifier: Modifier = Modifier,
    fields: List<TemplateField>,
    properties: SnapshotStateList<PropertyModel<*>?>
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
                                properties[index] = PropertyModel(field, it)
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
                                properties[index] = PropertyModel(field, n)
                            }
                        },
                        label = { Text(text = field.name) }
                    )
                }

                "range" -> {
                    val min = rememberSaveable { mutableIntStateOf(0) }
                    val max = rememberSaveable { mutableIntStateOf(1) }

                    Text(modifier = Modifier.align(Alignment.Start), text = field.name)

                    RangeField(
                        modifier = Modifier.width(280.dp),
                        min = min.intValue,
                        max = max.intValue,
                        onMinValueChange = {
                            val maxValue = max.intValue
                            if (it > 0) {
                                properties[index] = PropertyModel(
                                    field = field,
                                    value = Range(it, maxValue)
                                )
                                min.intValue = it
                            }
                        },
                        onMaxValueChange = {
                            val minValue = min.intValue

                            if (minValue > 0) {
                                properties[index] = PropertyModel(
                                    field = field,
                                    value = Range(minValue, it)
                                )
                                max.intValue = it
                            }
                        }
                    )
                }
            }
        }
    }
}