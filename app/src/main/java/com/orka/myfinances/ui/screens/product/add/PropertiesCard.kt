package com.orka.myfinances.ui.screens.product.add

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun PropertiesCard(
    modifier: Modifier = Modifier,
    fields: List<TemplateField>,
    properties: SnapshotStateList<PropertyModel<*>?>//TODO make it list
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        if (fields.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.properties)
                )

                Log.d("Properties(fields: ${fields.size})", "Size: ${properties.size}")
                if (properties.size != fields.size || properties.any { it == null }) {
                    HorizontalSpacer(8)
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            PropertiesList(
                modifier = Modifier.fillMaxWidth(),
                fields = fields,
                onSuccess = { model ->//TODO put it inside view model
                    Log.d("Properties(fields: ${fields.size})", "Size: ${properties.size}")
                    val index = properties.indexOfFirst { it?.fieldId == model.fieldId }
                    if (index == -1) {
                        Log.d("Properties", "Add a new model($model)")
                        properties.add(model)
                    }
                    else {
                        Log.d("Properties", "Replace model($model, index: $index)")
                        properties[index] = model
                    }
                },
                onFail = { id ->
                    val index = properties.indexOfFirst { it?.fieldId == id }
                    if (index != -1) properties.removeAt(index)
                }
            )

            VerticalSpacer(8)
        }
    }
}