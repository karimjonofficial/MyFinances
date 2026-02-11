package com.orka.myfinances.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.template.templateField1
import com.orka.myfinances.fixtures.resources.price
import com.orka.myfinances.fixtures.resources.salePrice
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun BasketItemCard(
    item: BasketItem,
    imageRes: Int,
    increase: () -> Unit,
    decrease: () -> Unit,
    remove: () -> Unit
) {
    val product = item.product

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(end = 32.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = product.title.name,
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
                                text = product.title.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            val propertiesText = product.title.properties
                                .joinToString(" | ") { "${it.type.name}: ${it.value}" }

                            Text(
                                text = propertiesText.ifEmpty {
                                    stringResource(R.string.no_description_provided)
                                },
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = product.title.description.description(),
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    VerticalSpacer(8)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$${product.title.defaultSalePrice}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = decrease,
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
                                text = "${item.amount}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            IconButton(
                                onClick = increase,
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
                    }
                }
            }

            IconButton(
                onClick = remove,
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
                    title = ProductTitle(
                        id = id1,
                        name = "Organic Cotton T-Shirt",
                        defaultPrice = 2500,
                        defaultSalePrice = 2500,
                        dateTime = dateTime,
                        category = Category(
                            id = Id(21),
                            name = "Clothes Warehouse",
                            template = clothesTemplate
                        ),
                        properties = listOf(
                            Property(Id(301), sizeField, "L"),
                            Property(Id(302), colorField, "Navy"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                            Property(id1, templateField1, "Y"),
                        ),
                        description = "Made with 100% GOTS certified organic cotton for a soft feel and comfortable fit."
                    ),
                    price = price,
                    salePrice = salePrice,
                    dateTime = dateTime
                ),
                amount = 2
            )
            val sampleHeadphones = BasketItem(
                product = Product(
                    id = Id(2),
                    title = ProductTitle(
                        id = id1,
                        name = "Wireless Noise-Cancelling Headphones",
                        defaultPrice = 19999,
                        defaultSalePrice = 14999,
                        dateTime = dateTime,
                        category = Category(
                            id = Id(22),
                            name = "Electronics Warehouse",
                            template = electronicsTemplate
                        ),
                        properties = listOf(
                            Property(Id(303), colorField, "Black")
                        ),
                        description = "Immerse yourself in sound with active noise cancellation and up to 30 hours of battery."
                    ),
                    price = price,
                    salePrice = salePrice,
                    dateTime = dateTime
                ),
                amount = 1
            )
            val sampleSneakers = BasketItem(
                product = Product(
                    id = Id(3),
                    title = ProductTitle(
                        id = id1,
                        dateTime = dateTime,
                        name = "Classic Canvas Sneakers",
                        category = Category(
                            id = Id(23),
                            name = "Shoes Warehouse",
                            template = shoesTemplate
                        ),
                        properties = listOf(
                            Property(Id(304), sizeField, "9"),
                            Property(Id(305), colorField, "Red")
                        ),
                        defaultPrice = 0,
                        defaultSalePrice = 0,
                        description = "A timeless sneaker design for any casual occasion."
                    ),
                    price = price,
                    salePrice = salePrice,
                    dateTime = dateTime
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

