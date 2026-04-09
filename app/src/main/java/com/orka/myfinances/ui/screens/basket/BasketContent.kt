package com.orka.myfinances.ui.screens.basket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.application.viewmodels.basket.toUiModel
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.resources.models.basket.basketItems
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.FooterSpacer
import com.orka.myfinances.lib.ui.contents.StateFulContent
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.basket.components.BasketItemCard

@Composable
fun BasketContent(
    modifier: Modifier = Modifier,
    state: State<BasketScreenModel>,
    interactor: BasketInteractor
) {
    StateFulContent(
        modifier = modifier,
        state = state,
        onRetry = interactor::refresh
    ) { modifier, model ->
        if (model.items.isNotEmpty()) {
            Column(modifier = modifier) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.items),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(items = model.items) { item ->
                        BasketItemCard(
                            item = item.model,
                            increase = { interactor.increase(item) },
                            decrease = { interactor.decrease(item) },
                            remove = { interactor.remove(item) }
                        )
                    }

                    item {
                        FooterSpacer()
                    }
                }

                PriceContent(
                    modifier = Modifier.fillMaxWidth(),
                    model = model,
                    interactor = interactor
                )
            }
        } else EmptyBasketContent(modifier)
    }
}

@Preview
@Composable
private fun BasketContentPreview() {
    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = "Basket"
    ) { paddingValues ->
        BasketContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = State.Success(
                BasketScreenModel(
                    items = basketItems.map {
                        it.toUiModel(
                            FormatPriceImpl(),
                            FormatDecimalImpl()
                        )
                    },
                    price = "100000.00 UZS"
                )
            ),
            interactor = BasketInteractor.dummy
        )
    }
}
