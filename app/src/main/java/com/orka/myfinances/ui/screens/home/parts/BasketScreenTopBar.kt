package com.orka.myfinances.ui.screens.home.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketInteractor
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreenTopBar(
    modifier: Modifier = Modifier,
    state: BasketState,
    interactor: BasketInteractor
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.basket)) },
        actions = {
            if (state is BasketState.Success && state.items.isNotEmpty()) {
                IconButton(onClick = { interactor.clear() }) {
                    Icon(
                        painter = painterResource(R.drawable.delete_outlined),
                        contentDescription = stringResource(R.string.clear)
                    )
                }
            }
        }
    )
}
