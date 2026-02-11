package com.orka.myfinances.ui.screens.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.screens.LazyColumnContent
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.history.components.ReceiveCard
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel

@Composable
fun ReceiveContent(
    modifier: Modifier = Modifier,
    viewModel: ReceiveContentViewModel,
    navigator: Navigator
) {
    val state = viewModel.state.collectAsState()

    LazyColumnContent(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        arrangementSpace = 0.dp,
        state = state.value,
        viewModel = viewModel,
        item = { modifier, receive ->
            ReceiveCard(
                modifier = modifier,
                receive = receive,
                onClick = { navigator.navigateToReceive(it) }
            )
        }
    )
}