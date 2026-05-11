package com.orka.myfinances.ui.screens.stock

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.resources.models.stockItem1
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun StockItemCard(
    modifier: Modifier = Modifier,
    item: StockItemCardModel,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Column(modifier = modifier.clip(RoundedCornerShape(24.dp))) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.furniture1),
                contentScale = ContentScale.Crop,
                contentDescription = item.title,
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 24.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .9f))
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = item.amount,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        VerticalSpacer(8)

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            VerticalSpacer(4)

            Text(
                text = item.price,
                style = MaterialTheme.typography.titleMedium
            )

            VerticalSpacer(8)

            if (item.basketAmount == null) {
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.shopping_cart_outlined),
                        contentDescription = "Add to Basket"
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier.clip(CircleShape),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.remove),
                            contentDescription = "Decrease"
                        )
                    }

                    Text(
                        text = item.basketAmount,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = onIncrease,
                        modifier = Modifier.clip(CircleShape),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = "Increase"
                        )
                    }
                }
            }
        }

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
                item = stockItem1.toCardModel(
                    formatPrice = FormatPriceImpl(),
                    formatDecimal = FormatDecimalImpl()
                ),
                onIncrease = {},
                onDecrease = {}
            )
        }
    }
}