package com.orka.myfinances.ui.screens.sale.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.contents.LazyColumnWithStickyHeaderContent

@Composable
fun SaleContent(
    modifier: Modifier = Modifier,
    interactor: SaleContentInteractor
) {
    val state = interactor.uiState.collectAsState()

    LazyColumnWithStickyHeaderContent(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        arrangementSpace = 0.dp,
        state = state.value,
        refresh = interactor::initialize,
        loadMore = interactor::loadMore,
        item = { sale ->
            SaleCard(
                sale = sale.model,
                onClick = { interactor.select(sale) }
            )
        }
    )
}