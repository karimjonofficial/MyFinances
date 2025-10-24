package com.orka.myfinances.ui.screens.warehouse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.fixtures.resources.models.stockItem1
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun StockItemCard(
    modifier: Modifier = Modifier,
    item: StockItem,
    click: (StockItem) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { click(item) }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {

            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.furniture1),
                contentScale = ContentScale.Crop,
                contentDescription = item.product.name,
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 24.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .9f))
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${item.amount} left",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        VerticalSpacer(8)

        Text(
            text = item.product.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )

        VerticalSpacer(4)

        Text(
            text = "${item.product.salePrice}",
            style = MaterialTheme.typography.titleMedium
        )

        VerticalSpacer(12)
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            StockItemCard(
                modifier = Modifier.size(height = 200.dp, width = 180.dp),
                item = stockItem1,
                click = {}
            )
        }
    }
}