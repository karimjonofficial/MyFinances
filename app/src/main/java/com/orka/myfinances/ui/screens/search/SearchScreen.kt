package com.orka.myfinances.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.BlankScreen

@Composable
fun SearchScreen(modifier: Modifier) {
    BlankScreen(
        modifier = modifier,
        title = stringResource(R.string.search)
    )
}