package com.orka.myfinances.ui.screens.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.fixtures.resources.models.product1
import com.orka.myfinances.lib.extensions.ui.toIconButtonColors
import com.orka.myfinances.lib.ui.components.HorizontalSpacer

@Composable
fun BasketItemCard(
    modifier: Modifier = Modifier,
    item: BasketItem,
    increase: (BasketItem) -> Unit,
    decrease: (BasketItem) -> Unit,
    remove: (BasketItem) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            ) {
                Image(
                    modifier = Modifier
                        .width(96.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(R.drawable.furniture),
                    contentScale = ContentScale.Crop,
                    contentDescription = item.product.name
                )

                HorizontalSpacer(8)
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(vertical = 2.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.product.name,
                        fontWeight = FontWeight.Medium
                    )

                    if (item.product.properties.isNotEmpty())
                        Text(
                            text = item.product.properties[0].value.toString(),
                            fontWeight = FontWeight.Light
                        )
                    else Text(
                        text = stringResource(R.string.no_properties),
                        fontWeight = FontWeight.Light
                    )

                    Text(
                        text = item.product.salePrice.toString(),
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = { remove(item) }) {
                    Icon(
                        painter = painterResource(R.drawable.delete_outlined),
                        contentDescription = null
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalIconButton(onClick = { decrease(item) }) {
                    Icon(
                        painter = painterResource(R.drawable.remove),
                        contentDescription = null
                    )
                }

                HorizontalSpacer(8)
                Text(text = item.amount.toString())

                HorizontalSpacer(8)
                IconButton(
                    colors = ButtonDefaults.buttonColors().toIconButtonColors(),
                    onClick = { increase(item) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        }
    }
}



@Preview
@Composable
private fun BasketItemCardPreview() {
    BasketItemCard(
        modifier = Modifier.width(400.dp),
        item = BasketItem(
            product = product1,
            amount = 1
        ),
        increase = {},
        decrease = {},
        remove = {},
    )
}