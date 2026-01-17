package com.orka.myfinances.lib.extensions.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@Composable
fun String?.description(defaultId: Int = R.string.no_description_provided): String {
    return this ?: stringResource(defaultId)
}