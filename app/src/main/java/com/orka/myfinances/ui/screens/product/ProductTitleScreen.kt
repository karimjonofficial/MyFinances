package com.orka.myfinances.ui.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.resources.models.product.productTitle1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.viewmodel.list.State
import com.orka.myfinances.ui.screens.debt.components.DescriptionCard
import com.orka.myfinances.ui.screens.product.viewmodel.ProductTitleScreenViewModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTitleScreen(
    modifier: Modifier = Modifier,
    state: State,
    viewModel: ProductTitleScreenViewModel
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

        when(state) {
            is State.Initial, is State.Loading -> LoadingScreen(modifier = m)
            is State.Failure -> FailureScreen(modifier = m)

            is State.Success<*> -> {
                val productTitle = state.value as ProductTitleModel
                LazyColumn(
                    modifier = m,
                    contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    item { HeroImage() }
                    item { TitleSection(productTitle = productTitle) }
                    item { PricingSection(productTitle = productTitle) }
                    item { HorizontalDivider() }
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

                if (dialogVisible.value) {
                    ReceiveDialog(
                        dismissRequest = { dialogVisible.value = false },
                        onSuccess = { p, sp, a, t, c ->
                            viewModel.receive(
                                price = p,
                                salePrice = sp,
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
    productTitle: ProductTitleModel
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {

        Text(
            text = productTitle.title,
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
    productTitle: ProductTitleModel
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {

        Text(
            text = productTitle.price,
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

@Preview
@Composable
private fun ProductTitleScreenPreview() {
    val viewModel = viewModel {
        ProductTitleScreenViewModel(
            productTitle = productTitle1,
            repository = { null },
            logger = DummyLogger(),
            loading = UiText.Res(R.string.loading),
            formatPrice = {""}, formatDecimal = {""}, formatDate = {""}
        )
    }
    val state = viewModel.uiState.collectAsState()
    
    MyFinancesTheme {
        ProductTitleScreen(
            state = state.value,
            viewModel = viewModel
        )
    }
}