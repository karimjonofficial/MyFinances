package com.orka.myfinances.ui.screens.product.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.models.types.Range
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.fixtures.resources.Types
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.RangeField

@Composable
fun PropertiesList(
    modifier: Modifier = Modifier,
    fields: List<TemplateField>,
    initialProperties: List<PropertyModel<*>> = emptyList(),
    onSuccess: (PropertyModel<*>) -> Unit,
    onFail: (Id) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        fields.forEach { field ->
            val initialValue = initialProperties.firstOrNull { it.fieldId == field.id }?.value

            when (field.type) {
                Types.TEXT -> {
                    val value = rememberSaveable(field.id.value, initialValue as? String) {
                        mutableStateOf(initialValue as? String ?: "")
                    }

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = value.value,
                        onValueChange = {
                            value.value = it
                            if (it.isNotBlank())
                                onSuccess(PropertyModel(field.id, it))
                            else onFail(field.id)
                        },
                        label = { Text(text = field.name) }
                    )
                }

                Types.NUMBER -> {
                    val value = rememberSaveable(field.id.value, initialValue as? Int) {
                        mutableStateOf(initialValue as? Int)
                    }

                    OutlinedIntegerTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = value.value,
                        onValueChange = {
                            value.value = it
                            if(it != null && it > 0)
                                onSuccess(PropertyModel(field.id, it))
                            else onFail(field.id)
                        },
                        label = { Text(text = field.name) }
                    )
                }

                Types.BOOLEAN -> {
                    val value = rememberSaveable(field.id.value, initialValue as? Boolean) {
                        mutableStateOf(initialValue as? Boolean ?: false)
                    }

                    LaunchedEffect(value.value) {
                        onSuccess(PropertyModel(field.id, value.value))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = field.name
                        )

                        HorizontalSpacer(8)
                        Switch(
                            checked = value.value,
                            onCheckedChange = {
                                value.value = it
                                onSuccess(PropertyModel(field.id, it))
                            }
                        )
                    }
                }

                Types.RANGE -> {
                    val initialRange = initialValue as? Range
                    val min = rememberSaveable(field.id.value, initialRange?.min) {
                        mutableStateOf(initialRange?.min)
                    }
                    val max = rememberSaveable(field.id.value, initialRange?.max) {
                        mutableStateOf(initialRange?.max)
                    }

                    RangeField(
                        modifier = Modifier.fillMaxWidth(),
                        title = field.name,
                        min = min.value,
                        max = max.value,
                        onMinValueChange = {
                            min.value = it
                            val maxValue = max.value
                            if (it != null && it > 0 && maxValue != null)
                                onSuccess(PropertyModel(field.id, Range(it, maxValue)))
                            else onFail(field.id)
                        },
                        onMaxValueChange = {
                            val minValue = min.value
                            max.value = it

                            if (minValue != null && minValue > 0 && it != null)
                                onSuccess(PropertyModel(field.id, Range(minValue, it)))
                            else onFail(field.id)
                        }
                    )
                }
            }
        }
    }
}