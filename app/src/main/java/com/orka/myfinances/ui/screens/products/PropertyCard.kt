package com.orka.myfinances.ui.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.data.models.product.Property

@Composable
fun PropertyCard(
    modifier: Modifier = Modifier,
    property: Property
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = property.type.name,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = property.value.toString(),
            fontWeight = FontWeight.SemiBold
        )
    }
}