package com.orka.myfinances.ui.screens.folder.home.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.SectionTitle
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.viewmodel.State
import com.orka.myfinances.ui.components.cards.StockItemCard
import com.orka.myfinances.ui.screens.stock.StockContentInteractor
import com.orka.myfinances.ui.screens.stock.StockItemUiModel

@Composable
fun StockItemsRow(
    modifier: Modifier = Modifier,
    title: String,
    state: State<ChunkUiModel<StockItemUiModel>>,
    interactor: StockContentInteractor
) {
    val chunk = state.value

    Column(modifier = modifier) {
        SectionTitle(
            modifier = Modifier.padding(start = 16.dp),
            text = title
        )

        VerticalSpacer(12)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (chunk != null) {
                val items = chunk.content.values.flatten()

                item {
                    HorizontalSpacer(8)
                }

                itemsIndexed(
                    items = items,
                    key = { _, item -> item.id.value }
                ) { index, item ->
                    if (index >= items.size - 3 && chunk.nextPageIndex != null && state !is State.Loading) {
                        LaunchedEffect(chunk.nextPageIndex) {
                            interactor.loadMore()
                        }
                    }

                    StockItemCard(
                        modifier = Modifier.size(150.dp, 300.dp),
                        item = item.model,
                        onIncrease = { interactor.addToBasket(item.id) },
                        onDecrease = { interactor.removeFromBasket(item.id) }
                    )
                }

                item {
                    HorizontalSpacer(8)
                }
            }

            if (state is State.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(240.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
