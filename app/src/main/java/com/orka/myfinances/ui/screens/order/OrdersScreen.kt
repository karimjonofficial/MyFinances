package com.orka.myfinances.ui.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnContentWithStickyHeader
import com.orka.myfinances.lib.viewmodel.list.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    state: State,
    viewModel: OrdersScreenViewModel
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.orders)) },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumnContentWithStickyHeader(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(0.dp),
            arrangementSpace = 0.dp,
            state = state,
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
            item = { modifier, item ->
                OrderCard(
                    modifier = modifier.padding(horizontal = 8.dp),
                    order = item.model,
                    onClick = { viewModel.select(item.order) }
                )
            }
        )
    }
}
