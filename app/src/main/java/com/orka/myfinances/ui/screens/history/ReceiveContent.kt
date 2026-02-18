package com.orka.myfinances.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.ui.screens.LazyColumnContentWithStickyHeader
import com.orka.myfinances.ui.screens.history.components.ReceiveCard
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel

@Composable
fun ReceiveContent(
    modifier: Modifier = Modifier,
    viewModel: ReceiveContentViewModel
) {
    val state = viewModel.uiState.collectAsState()

    LazyColumnContentWithStickyHeader(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        arrangementSpace = 0.dp,
        state = state.value,
        viewModel = viewModel,
        header = { modifier, date ->
            Text(
                text = date,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        },
        item = { modifier, receive ->
            ReceiveCard(
                modifier = modifier.padding(horizontal = 8.dp),
                receive = receive.model,
                onClick = { viewModel.select(receive.receive) }
            )
        }
    )
}