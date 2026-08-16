package com.orka.myfinances.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.stockItem1
import com.orka.myfinances.format.LocalFormatter
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.ui.map.toCardModel
import com.orka.myfinances.ui.models.card.StockItemCardModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun StockItemCard(
    modifier: Modifier = Modifier,
    item: StockItemCardModel,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val formatter = LocalFormatter.current

    Column(modifier = modifier.background(Color.Transparent)) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.furniture1),
                contentScale = ContentScale.FillHeight,
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
                    text = stringResource(R.string.left_f, formatter.formatNumber(item.amount)),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        VerticalSpacer(4)
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            if (!item.properties.isNullOrBlank()) {
                Text(
                    text = item.properties,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }

            VerticalSpacer(4)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.uzs_f, formatter.formatNumber(item.price)),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
        }

        VerticalSpacer(4)
        if (item.basketAmount == null) {
            Button(
                onClick = onIncrease,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_shopping_cart),
                    contentDescription = stringResource(R.string.add_to_cart)
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().height(36.dp).padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.height(36.dp).weight(1f),
                    onClick = onDecrease,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.remove),
                        contentDescription = stringResource(R.string.decrease)
                    )
                }

                HorizontalSpacer(16)
                Text(
                    text = formatter.formatNumber(item.basketAmount),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                HorizontalSpacer(16)
                IconButton(
                    modifier = Modifier.size(36.dp).weight(1f),
                    onClick = onIncrease,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    enabled = item.increaseEnabled
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = stringResource(R.string.increase)
                    )
                }
            }
        }
    }
}

@DefaultPreview
@Composable
private fun ProductCardPreview() {
    MyFinancesTheme {
        Surface {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                StockItemCard(
                    modifier = Modifier.size(150.dp, 300.dp),
                    item = stockItem1.toCardModel(),
                    onIncrease = {},
                    onDecrease = {}
                )
            }
        }
    }
}
