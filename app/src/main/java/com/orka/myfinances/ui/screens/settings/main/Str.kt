package com.orka.myfinances.ui.screens.settings.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@Composable
fun str(value: String?, fallbackValue: Int, isLoading: Boolean): String {
    return if(isLoading) {
        stringResource(R.string.loading)
    } else {
        if (value.isNullOrEmpty())
            stringResource(fallbackValue)
        else value
    }
}