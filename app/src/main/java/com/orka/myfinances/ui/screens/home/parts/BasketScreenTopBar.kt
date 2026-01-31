package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.BasketState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreenTopBar(
    modifier: Modifier = Modifier,
    viewModel: BasketContentViewModel
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.basket)) },
        actions = {
            val state = viewModel.uiState.collectAsState().value
            if (state is BasketState.Success && state.items.isNotEmpty())
                IconButton(onClick = { viewModel.clear() }) {
                    Icon(
                        painter = painterResource(R.drawable.delete_outlined),
                        contentDescription = stringResource(R.string.clear)
                    )
                }
        }
    )
}