package com.orka.myfinances.ui.screens.notification

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen

@Composable
fun NotificationScreen(
    modifier: Modifier,
    viewModel: NotificationScreenViewModel
) {
    LazyColumnScreen(
        modifier = modifier,
        title = stringResource(R.string.notifications),
        viewModel = viewModel,
        item = { modifier, notification ->
            NotificationCard(
                modifier = modifier,
                notification = notification,
                onClick = { viewModel.read(notification) }
            )
        }
    )
}