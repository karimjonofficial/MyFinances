package com.orka.myfinances.ui.screens.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.screens.LazyColumnContent
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.history.components.SaleCard
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel

@Composable
fun SaleContent(
    modifier: Modifier = Modifier,
    viewModel: SaleContentViewModel,
    navigator: Navigator
) {
    val state = viewModel.uiState.collectAsState()

    LazyColumnContent(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        arrangementSpace = 0.dp,
        state = state.value,
        viewModel = viewModel,
        item = { modifier, sale ->
            SaleCard(
                modifier = modifier,
                sale = sale,
                onClick = { navigator.navigateToSale(it) }
            )
        }
    )
}