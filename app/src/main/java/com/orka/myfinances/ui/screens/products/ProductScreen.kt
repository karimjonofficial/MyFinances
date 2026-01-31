package com.orka.myfinances.ui.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.lib.extensions.ui.description
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    product: Product,
    onEdit: () -> Unit,
    onAddToCart: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.product_details),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { onEdit() }) {
                        Icon(
                            painter = painterResource(R.drawable.edit_outlined),
                            contentDescription = "More"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomActionBar(onAddToCart = onAddToCart)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { HeroImage() }
            item { TitleSection(product = product) }
            item { PricingSection(product = product) }
            item { HorizontalDivider() }
            item { InventorySection(product = product) }
            item { DescriptionSection(product = product) }
            item { SpecificationsSection(product = product) }
        }
    }
}

@Composable
private fun HeroImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.headphone),
        contentDescription = null,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun TitleSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {

        Text(
            text = product.title.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        VerticalSpacer(8)
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painter = painterResource(R.drawable.schedule),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalSpacer(6)
            Text(
                text = product.dateTime.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PricingSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {

        Text(
            text = "$${product.salePrice}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalSpacer(12)
        Text(
            text = "$299.00",
            style = MaterialTheme.typography.titleMedium.copy(
                textDecoration = TextDecoration.LineThrough
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.weight(1f))
        AssistChip(
            onClick = {},
            label = { Text(stringResource(R.string.on_sale)) }
        )
    }
}

@Composable
private fun InventorySection(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "Inventory",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        VerticalSpacer(8)
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.inventory2),
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    HorizontalSpacer(12)
                    Column {

                        Text(
                            text = product.title.category.name,
                            style = MaterialTheme.typography.labelSmall
                        )

                        Text(
                            text = stringResource(R.string.in_stock),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {

                    Text(
                        text = stringResource(R.string.template),
                        style = MaterialTheme.typography.labelSmall
                    )

                    VerticalSpacer(4)
                    Text(
                        text = product.title.category.template.name,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DescriptionSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        VerticalSpacer(8)
        Text(
            text = product.description.description(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SpecificationsSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = stringResource(R.string.specifications),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        VerticalSpacer(8)
        Card {
            product.title.properties.forEachIndexed { index, property ->
                PropertyCard(property = property)
                if (index != product.title.properties.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun BottomActionBar(
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit
) {
    BottomAppBar(modifier = modifier) {
        Button(
            onClick = onAddToCart,
            modifier = Modifier.weight(2f)
        ) {
            Text(stringResource(R.string.add_to_cart))
        }
    }
}

@Preview
@Composable
private fun ProductScreenPreview() {
    ProductScreen(
        product = product1,
        onEdit = {},
        onAddToCart = {}
    )
}