package com.orka.myfinances.ui.screens.notification

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    modifier: Modifier,
    viewModel: NotificationScreenViewModel
) {
    LazyColumnScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.notifications)) }
            )
        },
        viewModel = viewModel,
        item = { modifier, item ->

            NotificationCard(
                modifier = modifier,
                notification = item,
                onClick = { viewModel.read(item) }
            )
        }
    )
}