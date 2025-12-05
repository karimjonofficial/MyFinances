package com.orka.myfinances.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.lib.ui.components.HorizontalSpacer

@Composable
fun BasketItemCard(
    item: BasketItem,
    imageRes: Int,
    increase: (BasketItem) -> Unit,
    decrease: (BasketItem) -> Unit,
    remove: (BasketItem) -> Unit
) {
    val product = item.product

    OutlinedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(product.properties.isNotEmpty()) {
                            product.properties.dropLast(1).forEach { property ->
                                Text(
                                    text = "${property.type.name}: ${property.value}",
                                    style = MaterialTheme.typography.bodySmall
                                )

                                HorizontalSpacer(4)
                                Text(text = "|")
                                HorizontalSpacer(4)
                            }

                            Text(
                                text = "${product.properties.last().type.name}: ${product.properties.last().value}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            HorizontalSpacer(4)
                        } else {
                            Text(
                                text = stringResource(R.string.no_description_provided),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    if (product.description.isNotBlank()) {
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    /**if (item.isOutOfStock) {
                        Text(
                            text = "Out of Stock",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    } else {**/
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$${product.salePrice}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            /**if (hasDiscount) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$${product.price / 100.0}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        textDecoration = TextDecoration.LineThrough,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                )
                            }**/
                        }
                    //}

                    Spacer(modifier = Modifier.height(8.dp))

                    //if(!item.isOutOfStock) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { decrease(item) },
                                //enabled = !item.isOutOfStock,
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.remove),
                                    contentDescription = stringResource(R.string.decrease)
                                )
                            }

                            Text(
                                text = item.amount.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            IconButton(
                                onClick = { increase(item) },
                                //enabled = !item.isOutOfStock,
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.add),
                                    contentDescription = stringResource(R.string.increase)
                                )
                            }
                        }
                    //}
                }
            }

            IconButton(
                onClick = { remove(item) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = stringResource(R.string.remove)
                )
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun BasketItemCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val sizeField = TemplateField(Id(201), "Size", "String")
            val colorField = TemplateField(Id(202), "Color", "String")
            val clothesTemplate = Template(
                id = Id(11),
                name = "T-Shirt Template",
                fields = listOf(sizeField, colorField)
            )
            val electronicsTemplate = Template(
                id = Id(12),
                name = "Headphones Template",
                fields = listOf(TemplateField(Id(203), "Color", "String"))
            )
            val shoesTemplate = Template(
                id = Id(13),
                name = "Sneakers Template",
                fields = listOf(sizeField, colorField)
            )
            val sampleTShirt = BasketItem(
                product = Product(
                    id = Id(1),
                    name = "Organic Cotton T-Shirt",
                    price = 2500,
                    salePrice = 2500,
                    warehouse = Warehouse(
                        id = Id(21),
                        name = "Clothes Warehouse",
                        template = clothesTemplate
                    ),
                    properties = listOf(
                        Property(Id(301), sizeField, "L"),
                        Property(Id(302), colorField, "Navy")
                    ),
                    description = "Made with 100% GOTS certified organic cotton for a soft feel and comfortable fit."
                ),
                amount = 2
            )
            val sampleHeadphones = BasketItem(
                product = Product(
                    id = Id(2),
                    name = "Wireless Noise-Cancelling Headphones",
                    price = 19999,
                    salePrice = 14999,
                    warehouse = Warehouse(
                        id = Id(22),
                        name = "Electronics Warehouse",
                        template = electronicsTemplate
                    ),
                    properties = listOf(
                        Property(Id(303), colorField, "Black")
                    ),
                    description = "Immerse yourself in sound with active noise cancellation and up to 30 hours of battery."
                ),
                amount = 1
            )
            val sampleSneakers = BasketItem(
                product = Product(
                    id = Id(3),
                    name = "Classic Canvas Sneakers",
                    price = 0,
                    salePrice = 0,
                    warehouse = Warehouse(
                        id = Id(23),
                        name = "Shoes Warehouse",
                        template = shoesTemplate
                    ),
                    properties = listOf(
                        Property(Id(304), sizeField, "9"),
                        Property(Id(305), colorField, "Red")
                    ),
                    description = "A timeless sneaker design for any casual occasion."
                ),
                amount = 1
            )

            BasketItemCard(
                imageRes = R.drawable.t_shirt,
                item = sampleTShirt,
                increase = {},
                decrease = {},
                remove = {}
            )

            BasketItemCard(
                imageRes = R.drawable.headphone,
                item = sampleHeadphones,
                increase = {},
                decrease = {},
                remove = {}
            )

            BasketItemCard(
                imageRes = R.drawable.sneakers,
                item = sampleSneakers,
                increase = {},
                decrease = {},
                remove = {}
            )
        }
    }
}

