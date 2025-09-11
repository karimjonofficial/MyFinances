package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenTopBar(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.app_name)) },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.size(width = 48.dp, height = 24.dp),
                contentDescription = stringResource(R.string.logo)
            )
        },
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(painter = painterResource(id = R.drawable.add), contentDescription = null)
            }

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.notifications_unread),
                    contentDescription = null
                )
            }
        }
    )
}