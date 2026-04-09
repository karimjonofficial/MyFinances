package com.orka.myfinances.ui.screens.templates.add

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTemplateScreenTopBar(
    modifier: Modifier = Modifier,
    interactor: AddTemplateScreenInteractor
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.templates)) },
        navigationIcon = {
            IconButton(onClick = { interactor.back() }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
    )
}