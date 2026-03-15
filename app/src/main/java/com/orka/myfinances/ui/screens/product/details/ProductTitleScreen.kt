package com.orka.myfinances.ui.screens.product.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.orka.myfinances.fixtures.resources.models.product.productTitle1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DescriptionCard
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.product.details.models.ProductTitleScreenModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTitleScreen(
    modifier: Modifier = Modifier,
    state: State,
    interactor: ProductTitleScreenInteractor
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

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
                    IconButton(onClick = {}) {//TODO
                        Icon(
                            painter = painterResource(R.drawable.edit_outlined),
                            contentDescription = stringResource(R.string.more)
                        )
                    }
                }
            )
        },
        bottomBar = {
            SingleActionBottomBar(
                buttonText = stringResource(R.string.receive),
                action = { dialogVisible.value = true }
            )
        }
    ) { paddingValues ->
        val m = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is State.Initial -> LoadingScreen(modifier = m, action = interactor::initialize)
            is State.Loading -> LoadingScreen(modifier = m)
            is State.Failure -> FailureScreen(modifier = m)

            is State.Success<*> -> {
                val productTitle = state.value as ProductTitleScreenModel
                LazyColumn(
                    modifier = m,
                    contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    item { HeroImage() }

                    item {
                        VerticalSpacer(16)
                        TitleSection(productTitle = productTitle)
                    }

                    item {
                        VerticalSpacer(8)
                        PricingSection(productTitle = productTitle)
                    }

                    item {
                        VerticalSpacer(8)
                        HorizontalDivider()
                    }

                    if (productTitle.properties.isNotEmpty()) {
                        item {
                            VerticalSpacer(16)
                            DividedList(
                                title = stringResource(R.string.specifications),
                                items = productTitle.properties,
                                itemTitle = { it.name },
                                itemSupportingText = { it.value }
                            )
                        }
                        if (!productTitle.description.isNullOrBlank()) {
                            item {
                                VerticalSpacer(8)
                                DescriptionCard(description = productTitle.description)
                            }
                        }
                    }
                }

                if (dialogVisible.value) {
                    val price = state.value.salePrice
                    ReceiveDialog(
                        dismissRequest = { dialogVisible.value = false },
                        price = price,
                        onSuccess = { a, t, c ->
                            interactor.receive(
                                amount = a,
                                totalPrice = t,
                                comment = c
                            )
                            dialogVisible.value = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.headphone),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun TitleSection(
    modifier: Modifier = Modifier,
    productTitle: ProductTitleScreenModel
) {
    Column(modifier = modifier) {
        Text(
            text = productTitle.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.schedule),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalSpacer(6)
            Text(
                text = productTitle.dateTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PricingSection(
    modifier: Modifier = Modifier,
    productTitle: ProductTitleScreenModel
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = productTitle.price,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun ProductTitleScreenPreview() {
    MyFinancesTheme {
        ProductTitleScreen(
            state = State.Success(
                productTitle1.toModel(
                    formatDecimal = { "100.00" },
                    formatDate = { "12.01.2024" },
                    formatPrice = { "1000.00 UZS" }
                )),
            interactor = ProductTitleScreenInteractor.dummy
        )
    }
}




